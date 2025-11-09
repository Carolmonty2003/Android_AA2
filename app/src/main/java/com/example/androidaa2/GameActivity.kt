package com.example.androidaa2

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar


/**
 * GameActivity
 * -------------
 * Pantalla de gameplay del ahorcado. Muestra:
 *  - TopBar reutilizable (Toolbar + menú con Settings).
 *  - Contenido del juego (palabra enmascarada, imagen del ahorcado y teclado A–Z).
 *  - Apertura de SettingsFragment en el mismo Activity (navegación por fragment con back stack).
 *
 * Uso responsable de IA (justificación):
 *  - IA-asistida para redactar comentarios y señalar mejoras
 */

class GameActivity : AppCompatActivity() {

    // --- Vistas principales del gameplay ---
    private lateinit var imgHangman: ImageView
    private lateinit var tvMasked: TextView
    private lateinit var tvResult: TextView
    private lateinit var btnBack: Button

    // --- TopBar y contenedores de navegación por fragment ---
    private lateinit var myToolbar: Toolbar
    private lateinit var frame: FrameLayout
    private lateinit var gameContent: View

    // --- Estado del juego ---
    private lateinit var target: String
    private var errors: Int = 0
    private val maxErrors: Int = 9
    private var finished: Boolean = false

    // IA-asistida: cálculo de índice ('A'..'Z') -> 0..25, validado con constantes del proyecto
    private val used: BooleanArray = BooleanArray(26) { false } //IA-asistida array de booleanos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // IA-asistida: aplicar Night Mode desde preferencias.
        val isNight: Boolean = getSharedPreferences("settings", MODE_PRIVATE)
            .getBoolean("night", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isNight) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )

        // --- Toolbar reutilizable + título ---
        myToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(myToolbar)
        supportActionBar?.title = getString(R.string.app_name)

        // --- Referencias a vistas de juego ---
        imgHangman = findViewById<ImageView>(R.id.imgAhorcado)
        tvMasked   = findViewById<TextView>(R.id.tvMasked)
        tvResult   = findViewById<TextView>(R.id.tvResult)
        btnBack    = findViewById<Button>(R.id.btnBack)

        // --- Alternar gameplay <-> settings ---
        frame = findViewById(R.id.frame)
        gameContent = findViewById(R.id.game)

        val received: String? = intent.getStringExtra("WORD")
        target = (received ?: "ANDROID").uppercase()

        updateMasked()
        updateAhorcado()

        // --- Tap para volver al selector cuando la partida ha terminado ---
        btnBack.setOnClickListener {
            if (finished) finish()
        }
    }

    /**
     * onKeyClick
     * -----------
     * Manejador común para los 26 botones A–Z (declarado en XML con android:onClick).
     * - Desactiva la letra pulsada.
     * - Actualiza estado (acierto/fallo) y repinta UI.
     * - Comprueba fin de partida (win/lose).
     */
    fun onKeyClick(view: View) {
        if (finished) return

        val btn: Button = view as Button //IA-asistida: coger char de boton
        val c: Char = btn.text.first().uppercaseChar() //IA_asistencia: ""
        val idx: Int = c - 'A' //IA-asistida: mapear 'A'..'Z' a 0..25
        if (idx !in 0..25) return
        if (used[idx]) return

        used[idx] = true
        // IA-asistida: deshabilitar visualmente los botones ya usados para mejorar la UX
        btn.isEnabled = false
        btn.alpha = 0.5f

        var hit: Boolean = false
        for (ch: Char in target) {
            if (ch == c) {
                hit = true
                break
            }
        }
        if (!hit) {
            errors++
            updateAhorcado()
        }

        updateMasked()
        checkEnd()
    }

    /**
     * updateMasked
     * -------------
     * Genera y muestra la representación con '_' para letras no adivinadas.
     * Mantiene espacios si la palabra los tuviera.
     */
    private fun updateMasked() { //IA-asistencia: mostrar ___ y actualizar en caso de acertar a la letra que pertoca
        val sb: StringBuilder = StringBuilder()
        for (ch: Char in target) {
            val show: Boolean = if (ch == ' ') true else used[ch - 'A']
            sb.append(if (show) ch else '_').append(' ')
        }
        tvMasked.text = sb.toString().trim()
    }

    /**
     * updateAhorcado
     * ---------------
     * Selecciona el drawable del ahorcado según el número de errores.
     * IA-asistida: podría mapearse con un IntArray para evitar el when (mejora contra magic numbers).
     */
    private fun updateAhorcado() {
        val resId: Int = when (errors) {
            0 -> R.drawable.ic_ahorcado_0
            1 -> R.drawable.ic_ahorcado_1
            2 -> R.drawable.ic_ahorcado_2
            3 -> R.drawable.ic_ahorcado_3
            4 -> R.drawable.ic_ahorcado_4
            5 -> R.drawable.ic_ahorcado_5
            6 -> R.drawable.ic_ahorcado_6
            7 -> R.drawable.ic_ahorcado_7
            8 -> R.drawable.ic_ahorcado_8
            9 -> R.drawable.ic_ahorcado_9
            else -> R.drawable.ic_ahorcado
        }
        imgHangman.setImageResource(resId)
    }

    /**
     * checkEnd
     * --------
     * Comprueba condiciones de fin:
     *  - Win: todas las letras reveladas.
     *  - Lose: errores >= maxErrors.
     * Muestra resultado con textos de strings.xml y deshabilita el teclado.
     */
    private fun checkEnd() {
        var allRevealed: Boolean = true
        for (ch: Char in target) {
            if (ch != ' ' && !used[ch - 'A']) {
                allRevealed = false
                break
            }
        }
        val lose: Boolean = errors >= maxErrors

        if (allRevealed || lose) {
            finished = true
            tvResult.visibility = View.VISIBLE
            // IA-asistida: textos desde strings.xml
            val winText: String = getString(R.string.you_win)
            val loseText: String = getString(R.string.you_lose)
            tvResult.text = if (allRevealed) winText else loseText
            btnBack.visibility = View.VISIBLE

            disableKeyboard()
        }
    }

    /**
     * disableKeyboard
     * ----------------
     * Deshabilita cualquier botón del teclado A–Z (excepto el botón de volver).
     * Recorre el árbol de vistas del contenedor principal del juego.
     */
    private fun disableKeyboard() {
        val root: ViewGroup = findViewById<View>(R.id.game) as ViewGroup
        fun disableAll(vg: ViewGroup) {
            for (i: Int in 0 until vg.childCount) {
                val v: View = vg.getChildAt(i)
                if (v is Button && v.id != R.id.btnBack) {
                    v.isEnabled = false
                    v.alpha = 0.5f
                } else if (v is android.view.ViewGroup) {
                    disableAll(v)
                }
            }
        }
        disableAll(root)
    }

    // --- Menú de la Toolbar (inflado y manejo del botón Settings) ---
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.custom_topbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                openSettings()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * openSettings
     * -------------
     * Oculta el contenido del juego y muestra el contenedor 'frame',
     * cargando en él el SettingsFragment con back stack (para volver con Atrás).
     */
    private fun openSettings() {
        gameContent.visibility = View.GONE
        frame.visibility = View.VISIBLE

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame, SettingsFragment())
            .addToBackStack("settings")
            .commit()

        supportActionBar?.title = getString(R.string.settings)
    }

    /**
     * onBackPressed
     * --------------
     * Si está visible SettingsFragment, vuelve al contenido del juego.
     * Si no, comportamiento normal (volver a la Activity anterior).
     */
    override fun onBackPressed() {
        if (frame.visibility == View.VISIBLE) {
            supportFragmentManager.popBackStack()
            frame.visibility = View.GONE
            gameContent.visibility = View.VISIBLE
            supportActionBar?.title = getString(R.string.app_name)
        } else {
            super.onBackPressed()
        }
    }
}
