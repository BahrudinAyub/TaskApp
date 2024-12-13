package com.example.taskapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UndangAdapter(private val anggotaList: List<AnggotaModel>) :
    RecyclerView.Adapter<UndangAdapter.UndangViewHolder>() {

    // List untuk melacak anggota yang dipilih
    private val selectedMembers = mutableListOf<AnggotaModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UndangViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_undang, parent, false)
        return UndangViewHolder(view)
    }

    override fun onBindViewHolder(holder: UndangViewHolder, position: Int) {
        val anggota = anggotaList[position]
        holder.bind(anggota, selectedMembers)
    }

    override fun getItemCount(): Int = anggotaList.size

    class UndangViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(anggota: AnggotaModel, selectedMembers: MutableList<AnggotaModel>) {
            val nameTextView = itemView.findViewById<TextView>(R.id.tvAnggotaName)
            val phoneTextView = itemView.findViewById<TextView>(R.id.tvAnggotaPhone)
            val profileImageView = itemView.findViewById<ImageView>(R.id.profileImageView)
            val checkBox = itemView.findViewById<CheckBox>(R.id.cbUndang)

            nameTextView.text = anggota.name
            phoneTextView.text = anggota.phone
            profileImageView.setImageResource(anggota.profileImageResId)

            // Set status CheckBox berdasarkan apakah anggota sudah dipilih atau belum
            checkBox.isChecked = selectedMembers.contains(anggota)

            // Tambah atau hapus anggota dari list ketika CheckBox diubah
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedMembers.add(anggota)
                } else {
                    selectedMembers.remove(anggota)
                }
            }
        }
    }
}
