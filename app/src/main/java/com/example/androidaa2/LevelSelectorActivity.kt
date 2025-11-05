package com.example.androidaa2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LevelSelectorActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView
    private val levels = listOf(
        Level(1, "ENTI"),
        Level(2, "TOMATE"),
        Level(3, "ESTERNOCLEIDOMASTOIDEO"),
        Level(4, "ANDROID"),
        Level(5, "KOTLIN"),
        Level(6, "NUBE"),
        Level(7, "LUNA"),
        Level(8, "MATERIAL"),
        Level(9, "COMPOSE"),
        Level(10, "AHORCADO"),
        // añade más para que haya scroll
        Level(11, "PANTALLA"),
        Level(12, "TECLADO"),
        Level(13, "JUEGO"),
        Level(14, "GANAR"),
        Level(15, "PERDER"),
        Level(16, "VECTOR"),
        Level(17, "RECYCLER"),
        Level(18, "NAVIGATE"),
        Level(19, "DEBUG"),
        Level(20, "SCROLL")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level_selector)

        rv = findViewById(R.id.rvLevels)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = LevelAdapter(levels) { level ->
            // TODO: abrir la pantalla de juego con la palabra seleccionada
            // startActivity(Intent(this, GameActivity::class.java).putExtra("WORD", level.word))
            Toast.makeText(this, "Seleccionado: ${level.word}", Toast.LENGTH_SHORT).show()
        }
    }
}