package com.example.taskapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GoalsAdapterAlternate(
    private val goalsList: List<Target>,
    private val onItemClicked: (Target) -> Unit
) : RecyclerView.Adapter<GoalsAdapterAlternate.GoalsViewHolderAlternate>() {

    class GoalsViewHolderAlternate(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val endDateTextView: TextView = itemView.findViewById(R.id.endDateTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalsViewHolderAlternate {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_goal_home, parent, false)
        return GoalsViewHolderAlternate(view)
    }

    override fun onBindViewHolder(holder: GoalsViewHolderAlternate, position: Int) {
        val goal = goalsList[position]
        holder.titleTextView.text = goal.namaTarget
        holder.endDateTextView.text = "Berlakhir: ${goal.tanggalSelesai}"

        holder.itemView.setOnClickListener {
            if (goal.namaTarget.isNullOrEmpty() || goal.tanggalSelesai.isNullOrEmpty()) {
                Log.e("GoalsAdapterAlternate", "Invalid data: $goal")
            } else {
                onItemClicked(goal)
            }
        }
    }

    override fun getItemCount(): Int = goalsList.size
}
