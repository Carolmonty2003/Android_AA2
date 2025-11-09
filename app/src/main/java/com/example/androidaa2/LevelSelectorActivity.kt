package com.example.androidaa2

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LevelSelectorActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView
    private lateinit var myToolbar: Toolbar
    private lateinit var frame: FrameLayout

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

        val isNight: Boolean = getSharedPreferences("settings", MODE_PRIVATE)
            .getBoolean("night", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isNight) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )

        rv = findViewById(R.id.rvLevels)
        frame = findViewById(R.id.frame)
        myToolbar = findViewById(R.id.toolbar)

        setSupportActionBar(myToolbar)
        supportActionBar?.title = getString(R.string.app_name)

        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = LevelAdapter(levels)
    }

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

    private fun openSettings() {
        rv.visibility = View.GONE
        frame.visibility = View.VISIBLE

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame, SettingsFragment())
            .addToBackStack("settings")
            .commit()

        supportActionBar?.title = getString(R.string.settings)
    }

    override fun onBackPressed() {
        if (frame.visibility == View.VISIBLE) {
            supportFragmentManager.popBackStack()
            frame.visibility = View.GONE
            rv.visibility = View.VISIBLE
            supportActionBar?.title = getString(R.string.app_name)
        } else {
            super.onBackPressed()
        }
    }
}