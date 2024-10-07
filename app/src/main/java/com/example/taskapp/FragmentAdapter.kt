package com.example.taskapp


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 4 // Jumlah fragment
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BerandaFragment()
            1 -> KalenderFragment()
            2 -> TugasFragment()
            3 -> ProfilFragment()
            else -> BerandaFragment()
        }
    }
}
