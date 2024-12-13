package com.example.taskapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class PartisipanAdapter(private val anggotaList: List<AnggotaModel>) :
    RecyclerView.Adapter<PartisipanAdapter.PartisipanViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartisipanViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_partisipan, parent, false)
        return PartisipanViewHolder(view)
    }

    override fun onBindViewHolder(holder: PartisipanViewHolder, position: Int) {
        val anggota = anggotaList[position]
        holder.bind(anggota)
    }

    override fun getItemCount(): Int = anggotaList.size

    class PartisipanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(anggota: AnggotaModel) {
            val nameTextView = itemView.findViewById<TextView>(R.id.tvAnggotaName)
            val phoneTextView = itemView.findViewById<TextView>(R.id.tvAnggotaPhone)
            val roleTextView =  itemView.findViewById<TextView>(R.id.tvAnggotaRole)
            val profileImageView = itemView.findViewById<ImageView>(R.id.profileImageView)

            nameTextView.text = anggota.name
            roleTextView.text = anggota.role
            phoneTextView.text = anggota.phone
            profileImageView.setImageResource(anggota.profileImageResId)
        }
    }
}
