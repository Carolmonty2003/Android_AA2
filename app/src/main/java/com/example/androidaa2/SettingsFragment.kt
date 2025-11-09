package com.example.androidaa2

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val checkBox: CheckBox = view.findViewById(R.id.nightMode)

        val prefs: SharedPreferences = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        val isNight: Boolean = prefs.getBoolean("night", false)
        checkBox.isChecked = isNight

        checkBox.setOnCheckedChangeListener { _, checked: Boolean ->
            if (checked == isNight) return@setOnCheckedChangeListener //return@setOnCheckedChangeListener arregla bug parpadeo de cuando cambias dentro de un nivel.

            prefs.edit().putBoolean("night", checked).apply()

            AppCompatDelegate.setDefaultNightMode(
                if (checked) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }
    }
}
