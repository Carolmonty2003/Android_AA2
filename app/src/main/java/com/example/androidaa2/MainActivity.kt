package com.example.androidaa2

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

/**
 * MainActivity
 * -------------
 * Pantalla de menú principal (no es Splash). Muestra:
 *  - Título e imagen del juego.
 *  - Un área táctil (layout raíz con id @id/main) que al pulsarse
 *    navega al selector de niveles (LevelSelectorActivity).
 *
 * Uso responsable de IA:
 *  - IA-asistida para redactar y estandarizar comentarios y para recordar
 *    el orden correcto del Night Mode.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val isNight: Boolean = getSharedPreferences("settings", MODE_PRIVATE)
            .getBoolean("night", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isNight) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )


        findViewById<View>(R.id.main).setOnClickListener {
            startActivity(Intent(this, LevelSelectorActivity::class.java))
        }
    }
}
