package com.example.taskapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TugasAdapter(private var tugasList: List<Tugas>) :
    RecyclerView.Adapter<TugasAdapter.TugasViewHolder>() {

    class TugasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTitle: TextView = itemView.findViewById(R.id.textTitle)
        val textTime: TextView = itemView.findViewById(R.id.textTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TugasViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tugas, parent, false)
        return TugasViewHolder(view)
    }

    override fun onBindViewHolder(holder: TugasViewHolder, position: Int) {
        val tugas = tugasList[position]
        holder.textTitle.text = tugas.title
        holder.textTime.text = tugas.time
    }

    override fun getItemCount(): Int {
        return tugasList.size
    }

    fun updateList(newTugasList: List<Tugas>) {
        tugasList = newTugasList
        notifyDataSetChanged()
    }
}
