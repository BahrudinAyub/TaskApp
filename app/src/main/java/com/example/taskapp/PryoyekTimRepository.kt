import android.content.Context
import android.content.SharedPreferences

class ProjectPreferences(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("project_preferences", Context.MODE_PRIVATE)

    private val KEY_PROJECT_LIST = "project_list"

    // Save a single project to the list
    fun saveProjectData(newProject: ProjectData) {
        val projectList = getProjectList().toMutableList() // Ambil list lama
        projectList.add(newProject) // Tambahkan proyek baru

        // Serialize list into a single string
        val serializedData = projectList.joinToString("||") { project ->
            "${project.projectName}::${project.projectDescription}::${project.specificDate ?: ""}" +
                    "::${project.activityDays ?: ""}::${project.restDays ?: ""}::${project.startDate ?: ""}" +
                    "::${project.endDate ?: ""}::${project.reminderCount ?: ""}::${project.priority ?: ""}" +
                    "::${project.postponeTask}"
        }

        // Save serialized string to SharedPreferences
        with(sharedPreferences.edit()) {
            putString(KEY_PROJECT_LIST, serializedData)
            apply()
        }
    }

    // Retrieve all projects as a list
    fun getProjectList(): List<ProjectData> {
        val serializedData = sharedPreferences.getString(KEY_PROJECT_LIST, "") ?: ""
        if (serializedData.isEmpty()) return emptyList()

        // Deserialize string back to list of ProjectData
        return serializedData.split("||").map { entry ->
            val parts = entry.split("::")
            ProjectData(
                projectName = parts[0],
                projectDescription = parts[1],
                specificDate = parts[2].ifEmpty { null },
                activityDays = parts[3].toIntOrNull(),
                restDays = parts[4].toIntOrNull(),
                startDate = parts[5].ifEmpty { null },
                endDate = parts[6].ifEmpty { null },
                reminderCount = parts[7].toIntOrNull(),
                priority = parts[8].ifEmpty { null },
                postponeTask = parts[9].toBoolean()
            )
        }
    }

    // Clear all projects
    fun clearAllProjects() {
        with(sharedPreferences.edit()) {
            remove(KEY_PROJECT_LIST)
            apply()
        }
    }
}
