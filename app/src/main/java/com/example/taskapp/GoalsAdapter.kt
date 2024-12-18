package com.example.taskapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GoalsAdapter(
    private val goalsList: List<Target>,
    private val onItemClicked: (Target) -> Unit
) : RecyclerView.Adapter<GoalsAdapter.GoalsViewHolder>() {

    class GoalsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val subtitleTextView: TextView = itemView.findViewById(R.id.subtitleTextView)
        val endDateTextView: TextView = itemView.findViewById(R.id.endDateTextView)
        val circularProgressBar: ProgressBar = itemView.findViewById(R.id.circularProgressBar)
        val progressPercentageTextView: TextView =
            itemView.findViewById(R.id.progressPercentageTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_goal, parent, false)
        return GoalsViewHolder(view)
    }

    override fun onBindViewHolder(holder: GoalsViewHolder, position: Int) {
        val goal = goalsList[position]
        holder.titleTextView.text = goal.namaTarget
        holder.subtitleTextView.text = goal.deskripsi
        holder.endDateTextView.text = "Berlakhir: ${goal.tanggalSelesai}"
        holder.circularProgressBar.progress = 40  // Dummy progress
        holder.progressPercentageTextView.text = "40%"

        holder.itemView.setOnClickListener {
            if (goal.namaTarget.isNullOrEmpty() || goal.deskripsi.isNullOrEmpty() || goal.tanggalSelesai.isNullOrEmpty()) {
                Log.e("GoalsAdapter", "Invalid data: $goal")
            } else {
                onItemClicked(goal)
            }
        }

    }

    override fun getItemCount(): Int = goalsList.size
}

