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

    // Lista
    private val levels = listOf(
        Level(1, "ENTI"),
        Level(2, "TETRIS"),
        Level(3, "SPACEINVADERS"),
        Level(4, "DOOM"),
        Level(5, "CENTIPEDE"),
        Level(6, "SONIC"),
        Level(7, "ROGUE"),
        Level(8, "DONKEYKONG"),
        Level(9, "PITFALL"),
        Level(10, "POLEPOSITION"),
        Level(11, "OUTRUN"),
        Level(12, "ZELDA"),
        Level(13, "PANG"),
        Level(14, "PRINCEOFPERSIAN"),
        Level(15, "MANICMINER"),
        Level(16, "ELITE"),
        Level(17, "GTA"),
        Level(18, "STREETFIGHTERII"),
        Level(19, "PONG"),
        Level(20, "ABADIADELCRIMEN")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level_selector)

        rv = findViewById(R.id.rvLevels)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = LevelAdapter(levels)
    }
}