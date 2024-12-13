package com.example.taskapp

import android.content.Intent
import android.os.Bundle
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class ProjectDetailActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var tvProgressPercentage: TextView
    private lateinit var checkBoxes: List<CheckBox>
    private var totalTasks = 5 // Total number of sub-tasks
    private lateinit var addTeamIcon: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_detail)

        // Inisialisasi ImageView
        addTeamIcon = findViewById(R.id.addTeamIcon)

        // Set onClickListener untuk ImageView
        addTeamIcon.setOnClickListener {
            // Navigasi ke halaman AnggotaTeam
            val intent = Intent(this, AnggotaTeam::class.java)
            startActivity(intent)
        }

        // Initialize Progress Bar and Progress Percentage
        progressBar = findViewById(R.id.progressBar)
        tvProgressPercentage = findViewById(R.id.tvProgressLabel)

        // Initialize the checkboxes for each sub-task
        val subTask1 = findViewById<CheckBox>(R.id.subtask1)
        val subTask2 = findViewById<CheckBox>(R.id.subtask2)
        val subTask3 = findViewById<CheckBox>(R.id.subtask3)
        val subTask4 = findViewById<CheckBox>(R.id.subtask4)
        val subTask5 = findViewById<CheckBox>(R.id.subtask5)
        // Add other sub-tasks similarly...

        checkBoxes = listOf(subTask1, subTask2 ,subTask3, subTask4, subTask5 )

        // Set up listeners for each checkbox
        for (checkBox in checkBoxes) {
            checkBox.setOnCheckedChangeListener { _, _ ->
                updateProgress()
            }
        }

        // Initialize the progress based on initial checked status
        updateProgress()

    }

    // Function to update the progress bar and text
    private fun updateProgress() {
        // Calculate the number of checked tasks
        val completedTasks = checkBoxes.count { it.isChecked }

        // Calculate the progress percentage
        val progressPercentage = (completedTasks.toDouble() / totalTasks) * 100

        // Update the progress bar and the percentage text
        progressBar.progress = progressPercentage.toInt()
        tvProgressPercentage.text = "${progressPercentage.toInt()}%"
    }
}
