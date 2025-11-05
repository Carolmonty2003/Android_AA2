package com.example.androidaa2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LevelAdapter(
    private val items: List<Level>,
    private val onClick: (Level) -> Unit
) : RecyclerView.Adapter<LevelAdapter.VH>() {

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val tvWord: TextView = view.findViewById(R.id.tvWord)
        val tvLetters: TextView = view.findViewById(R.id.tvLetters)
        val imgDifficulty: ImageView = view.findViewById(R.id.imgDifficulty)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_layout_manager, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val level = items[position]
        holder.tvWord.text = "Palabra: ${level.word}"
        holder.tvLetters.text = "Letras: ${level.lettersCount}"

        val icon = when (level.difficulty) {
            Difficulty.EASY -> R.drawable.ic_easy
            Difficulty.MEDIUM -> R.drawable.ic_medium
            Difficulty.HARD -> R.drawable.ic_hard
        }
        holder.imgDifficulty.setImageResource(icon)

        holder.itemView.setOnClickListener { onClick(level) }
    }

    override fun getItemCount() = items.size
}
