package com.example.androidaa2

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment


class GameActivity : AppCompatActivity() {

    private lateinit var imgHangman: ImageView
    private lateinit var tvMasked: TextView
    private lateinit var tvResult: TextView
    private lateinit var btnBack: Button
    private lateinit var myToolbar: Toolbar

    private lateinit var target: String
    private var errors: Int = 0
    private val maxErrors: Int = 9
    private var finished: Boolean = false

    // Marcamos letras pulsadas A..Z -> 26 posiciones
    private val used: BooleanArray = BooleanArray(26) { false } //chatgpt: pregunte como se hacia en kt un array de booleanos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        imgHangman = findViewById<ImageView>(R.id.imgAhorcado)
        tvMasked   = findViewById<TextView>(R.id.tvMasked)
        tvResult   = findViewById<TextView>(R.id.tvResult)
        btnBack    = findViewById<Button>(R.id.btnBack)

        val received: String? = intent.getStringExtra("WORD")
        target = (received ?: "ANDROID").uppercase()

        updateMasked()
        updateAhorcado()

        btnBack.setOnClickListener {
            if (finished) finish() //chatgpt finish() para volver a la actividad anterior el selector de niveles provocando
                                   //el ciclo de vida onPause(), onStop() y por ultimo onDestroy(), lo que hace que se cierre
                                   // la actividad y saca la ultima de la backstack de la pila de pantallas
        }
        myToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(myToolbar)
    }

    fun onKeyClick(view: View) {
        if (finished) return

        val btn: Button = view as Button //chat gpt pillar char de boton
        val c: Char = btn.text.first().uppercaseChar() //chat gpt: pillar char de boton
        val idx: Int = c - 'A' //chat gpt
        if (idx !in 0..25) return
        if (used[idx]) return

        used[idx] = true
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

    private fun updateMasked() { //chatgpt: mostrar ___ y actualizar en caso de acertar a la letra que pertoca
        val sb: StringBuilder = StringBuilder()
        for (ch: Char in target) {
            val show: Boolean = if (ch == ' ') true else used[ch - 'A']
            sb.append(if (show) ch else '_').append(' ')
        }
        tvMasked.text = sb.toString().trim()
    }

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
            tvResult.text = if (allRevealed) "You Win" else "You Lose"
            btnBack.visibility = View.VISIBLE //.VISIBLE chat gpt

            disableKeyboard()
        }
    }

    private fun disableKeyboard() { //chatgpt: deshabilitar teclas teclado
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.custom_topbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.settings)
        {
            //loadFragment(SettingsFragment())
            true
        }
        else
        {
            super.onOptionsItemSelected(item)
        }
    }

    //private fun loadFragment(fragment: Fragment){
    //    supportFragmentManager.beginTransaction().replace(R.id.frame,fragment).commit()
    //}
}
