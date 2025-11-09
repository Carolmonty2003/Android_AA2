package com.example.androidaa2

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment

/**
 * SettingsFragment
 * ----------------
 * Fragment de ajustes que permite alternar el tema de la app entre Light/Dark.
 * Se carga dentro de la Activity anfitriona en un FrameLayout (patrón Toolbar+Fragment
 * como en los PDFs: replace + addToBackStack).
 *
 * Uso responsable de IA:
 *  - IA-asistida para redactar comentarios y recordar la secuencia correcta:
 *    leer prefs -> setear UI -> listener que guarda y llama a AppCompatDelegate.
 *  - Revisión y adaptación final alineada con los apuntes de clase.
 */
class SettingsFragment : Fragment(R.layout.fragment_settings) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val checkBox: CheckBox = view.findViewById(R.id.nightMode)

        val prefs: SharedPreferences = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        val isNight: Boolean = prefs.getBoolean("night", false)
        checkBox.isChecked = isNight

        checkBox.setOnCheckedChangeListener { _, checked: Boolean ->
            if (checked == isNight) return@setOnCheckedChangeListener //IA-asistencia: return@setOnCheckedChangeListener arregla bug parpadeo de cuando cambias dentro de un nivel.

            prefs.edit().putBoolean("night", checked).apply()

            AppCompatDelegate.setDefaultNightMode(
                if (checked) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }
    }
}
