package com.example.selaluada.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.selaluada.R
import com.example.selaluada.data.model.PlafondLevel
import androidx.cardview.widget.CardView


class PlafondAdapter(private val list: List<PlafondLevel>) : RecyclerView.Adapter<PlafondAdapter.PlafondViewHolder>() {

    inner class PlafondViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvLevel: TextView = itemView.findViewById(R.id.tvLevel)
        val tvLimitPinjaman: TextView = itemView.findViewById(R.id.tvLimit)
        val tvLimitTenor: TextView = itemView.findViewById(R.id.tvTenor)
        val tvBunga: TextView = itemView.findViewById(R.id.tvBunga)
        val cardView: CardView = itemView.findViewById(R.id.cardView) // Tambahkan ini
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlafondViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_plafond, parent, false)
        return PlafondViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlafondViewHolder, position: Int) {
        val item = list[position]
        holder.tvLevel.text = "${item.level}"
        holder.tvLimitPinjaman.text = "Limit Pinjaman : ${item.limitPinjaman}"
        holder.tvLimitTenor.text = "Limit Tenor : ${item.limitTenor}"
        holder.tvBunga.text = "Bunga : ${item.bunga}"

        val backgroundRes = when (item.level.lowercase()) {
            "diamond" -> R.drawable.card_diamond
            "emerald" -> R.drawable.card_emerald
            "ruby" -> R.drawable.card_ruby
            "sapphire" -> R.drawable.card_sapphire
            "topaz" -> R.drawable.card_topaz
            else -> android.R.color.white
        }

        holder.cardView.setBackgroundResource(backgroundRes)

    }

    override fun getItemCount(): Int = list.size
}
