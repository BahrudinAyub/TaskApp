package com.example.taskapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.taskapp.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager = binding.viewPager

        // Set up ViewPager with FragmentAdapter
        val adapter = FragmentAdapter(this)
        viewPager.adapter = adapter

        val bn = binding.bottomNavigationView

        // Set up Bottom Navigation
        bn.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nav_home -> {
                    viewPager.currentItem = 0
                    true
                }
                R.id.nav_search -> {
                    viewPager.currentItem = 1
                    true

                }
                R.id.nav_notifications -> {
                    viewPager.currentItem = 2
                    true

                }
                R.id.nav_profile ->  {
                    viewPager.currentItem = 3
                    true

                }
            }
            false
        }

        // Set up ViewPager page change listener to change the selected bottom navigation item
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bn.menu.getItem(position).isChecked =true
            }
        })
    }
}
