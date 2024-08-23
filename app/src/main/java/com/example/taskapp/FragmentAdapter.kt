package com.example.taskapp

import InspirasiFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 4 // Jumlah fragment
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CatatanFragment()
            1 -> TugasFragment()
            2 -> InspirasiFragment()
            3 -> ProfilFragment()
            else -> CatatanFragment()
        }
    }
}
