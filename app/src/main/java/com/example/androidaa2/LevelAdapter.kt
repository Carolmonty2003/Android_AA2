package com.example.androidaa2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LevelAdapter(private val items: List<Level>)
    : RecyclerView.Adapter<LevelAdapter.VH>() {

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
        val len = level.lettersCount()
        val context = holder.itemView.context

        holder.tvWord.text = "${context.getString(R.string.word)}: ${level.word}" //chat gpt para variar texto segun localizacion
        holder.tvLetters.text = "${context.getString(R.string.letters)}: $len" //chat gpt para variar texto segun localizacion

        val iconRes = when (len) {
            in 1..4 -> R.drawable.ic_easy
            in 5..7 -> R.drawable.ic_medium
            else    -> R.drawable.ic_hard
        }
        holder.imgDifficulty.setImageResource(iconRes)

        holder.itemView.setOnClickListener {
            val ctx = it.context
            val i = android.content.Intent(ctx, GameActivity::class.java)
            i.putExtra("WORD", level.word.uppercase())
            ctx.startActivity(i)
        }
    }

    override fun getItemCount() = items.size
}
