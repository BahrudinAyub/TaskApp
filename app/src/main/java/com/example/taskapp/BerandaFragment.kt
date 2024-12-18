package com.example.taskapp

import ProjectAdapter
import ProjectData
import ProjectPreferences
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.taskapp.databinding.FragmentCatatanBinding
import java.text.SimpleDateFormat
import java.util.*

class BerandaFragment : Fragment() {

    private var _binding: FragmentCatatanBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCatatanBinding.inflate(inflater, container, false)
        val view = binding.root

        // Set onClickListener for FloatingActionButton (existing functionality)
        setupFab()

        // Set current date
        setCurrentDate()

        // Retrieve and display the habit task names
        displayHabitTasks()

        // Pastikan kode ini berada di dalam Activity atau Fragment

// Inisialisasi SharedPreferencesManager
        val projectPreferences = ProjectPreferences(requireContext())
        val sharedPreferencesManager = SharedPreferencesManager(requireContext())

// Ambil RecyclerView dan Tombol dari binding
        val projectRecyclerView = binding.recyclerViewProjects
        val goalsRecyclerView = binding.recyclerViewGoals
        val buttonTim = binding.buttonTim
        val buttonGoals = binding.buttonGoals

// Tambahkan data contoh jika kosong
        if (projectPreferences.getProjectList().isEmpty()) {
            projectPreferences.saveProjectData(ProjectData("Laravel", "Deskripsi Laravel"))
            projectPreferences.saveProjectData(ProjectData("Tugas Kotlin", "Deskripsi Kotlin"))
        }

// Ambil data proyek
        val projectList = projectPreferences.getProjectList().toMutableList()

// Setup RecyclerView untuk proyek
        projectRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val projectAdapter = ProjectAdapter(projectList) { project ->
            val intent = Intent(activity, ProjectDetailActivity::class.java).apply {
                putExtra("project_name", project.projectName)
                putExtra("project_description", project.projectDescription)
            }
            startActivity(intent)
        }

        projectRecyclerView.adapter = projectAdapter
        projectAdapter.notifyDataSetChanged()

// Ambil data goalsList secara dinamis
        val goalsList = sharedPreferencesManager.getTargetList()
        Log.d("GoalsFragment", "Goals List: $goalsList")

// Setup RecyclerView untuk goals
        goalsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val goalsAdapter = GoalsAdapterAlternate(goalsList) { goal ->
            val intent = Intent(activity, TargetActivity::class.java).apply {
                putExtra("goal_name", goal.namaTarget)
                putExtra("goal_description", goal.deskripsi)
                putExtra("goal_end_date", goal.tanggalSelesai)
            }
            startActivity(intent)
        }

        goalsRecyclerView.adapter = goalsAdapter
        goalsAdapter.notifyDataSetChanged()

        // Atur Tombol Tim
        buttonTim.setOnClickListener {
            // Tampilkan RecyclerView Projects, sembunyikan Goals
            projectRecyclerView.visibility = View.VISIBLE
            goalsRecyclerView.visibility = View.GONE

            // Gaya tombol aktif untuk Tim
            buttonTim.setBackgroundResource(R.drawable.button_unselected)
            buttonTim.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))

            // Gaya tombol default untuk Goals
            buttonGoals.setBackgroundResource(R.drawable.button_selected)
            buttonGoals.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_text))
        }

// Atur Tombol Goals
        buttonGoals.setOnClickListener {
            // Tampilkan RecyclerView Goals, sembunyikan Projects
            goalsRecyclerView.visibility = View.VISIBLE
            projectRecyclerView.visibility = View.GONE

            // Gaya tombol aktif untuk Goals
            buttonGoals.setBackgroundResource(R.drawable.button_unselected)
            buttonGoals.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))

            // Gaya tombol default untuk Tim
            buttonTim.setBackgroundResource(R.drawable.button_selected)
            buttonTim.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_text))
        }





        return view
    }


    private fun setCurrentDate() {
        // Get the current date
        val currentDate = Calendar.getInstance().time

        // Format the date to display as "EEEE, dd MMMM yyyy" (e.g., "Selasa, 21 April 2024")
        val dateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("id"))

        // Set the formatted date to the TextView
        binding.tvDate.text = dateFormat.format(currentDate)
    }

    private fun displayHabitTasks() {
        // Buat instance dari TugasBaruSharedPreferencesManager
        val sharedPreferencesManager = TugasBaruSharedPreferencesManager(requireContext())

        // Ambil semua tugas dari SharedPreferencesManager
        val tasks = sharedPreferencesManager.getTaskList() // Gunakan getTaskList()

        // Set up RecyclerView dengan TaskAdapter
        val taskAdapter = TaskAdapter(tasks) { task ->
            // Handle task completion (e.g., remove from list)
            val updatedTasks = tasks.toMutableList().apply { remove(task) }
            // Update SharedPreferences
            sharedPreferencesManager.saveTaskList(updatedTasks)
            // Refresh RecyclerView
            displayHabitTasks()
        }

        binding.rvDailyTasks.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }




    // Function to save updated habit tasks
    private fun saveUpdatedTasks(updatedTasks: Set<String>) {
        val sharedPref = requireContext().getSharedPreferences("TaskApp", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putStringSet("habit_task_names", updatedTasks)
            apply()
        }
    }

    // Function to save updated TugasBerulang tasks
    private fun saveUpdatedTugas(updatedTasks: Set<String>) {
        val sharedPref = requireContext().getSharedPreferences("TaskApp", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putStringSet("task_names", updatedTasks)
            apply()
        }
    }

    // Function to remove the new task (TugasBerulang) from SharedPreferences
    private fun removeTugasBerulang(index: Int) {
        val sharedPref = requireContext().getSharedPreferences("TaskApp", Context.MODE_PRIVATE)
        val taskNames =
            sharedPref.getStringSet("task_names", setOf())?.toMutableSet() ?: mutableSetOf()
        val reminderTimes =
            sharedPref.getStringSet("reminder_times", setOf())?.toMutableSet() ?: mutableSetOf()
        val priorities =
            sharedPref.getStringSet("priorities", setOf())?.toMutableSet() ?: mutableSetOf()

        if (index < taskNames.size) {
            taskNames.remove(taskNames.elementAt(index))
            reminderTimes.remove(reminderTimes.elementAt(index))
            priorities.remove(priorities.elementAt(index))

            with(sharedPref.edit()) {
                putStringSet("task_names", taskNames)
                putStringSet("reminder_times", reminderTimes)
                putStringSet("priorities", priorities)
                apply()
            }
        }
    }

    private fun setupFab() {
        binding.fabAddTask.setOnClickListener {
            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.dialog_task_option)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            val optionTugas = dialog.findViewById<LinearLayout>(R.id.option_tugas)
            val optionTugasBerulang = dialog.findViewById<LinearLayout>(R.id.option_tugas_berulang)
            val optionKebiasaan = dialog.findViewById<LinearLayout>(R.id.option_kebiasaan)
            val optionTarget =
                dialog.findViewById<LinearLayout>(R.id.option_target) // Tambahkan komponen Target
            val optionProyekTim = dialog.findViewById<LinearLayout>(R.id.option_proyek_tim)
            optionTugas.setOnClickListener {
                val intent = Intent(activity, TugasBaruActivity::class.java)
                startActivity(intent)
                dialog.dismiss()
            }

            optionTugasBerulang.setOnClickListener {
                val intent = Intent(activity, TugasBerulangActivity::class.java)
                startActivity(intent)
                dialog.dismiss()
            }

            optionKebiasaan.setOnClickListener {
                val intent = Intent(activity, KebiasaanBaruActivity::class.java)
                startActivity(intent)
                dialog.dismiss()
            }

            optionTarget.setOnClickListener { // Tambahkan handler klik untuk Target
                val intent = Intent(activity, TargetBaruActivity::class.java)
                startActivity(intent)
                dialog.dismiss()
            }
            optionProyekTim.setOnClickListener { // Tambahkan handler klik untuk Target
                val intent = Intent(activity, ProyekTimActivity::class.java)
                startActivity(intent)
                dialog.dismiss()
            }

            dialog.show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
