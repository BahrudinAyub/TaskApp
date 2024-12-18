import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskapp.R

class ProjectAdapter(
    private val projectList: List<ProjectData>,
    private val onItemClick: (ProjectData) -> Unit
) : RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>() {

    inner class ProjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val projectName: TextView = itemView.findViewById(R.id.tv_project_name)
        val projectProgress: TextView = itemView.findViewById(R.id.tv_project_progress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_project_card, parent, false)
        return ProjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val project = projectList[position]
        holder.projectName.text = project.projectName
        holder.projectProgress.text = "Progress: 50%" // Contoh progress statis

        holder.itemView.setOnClickListener {
            onItemClick(project) // Kirim data proyek yang diklik
        }
    }

    override fun getItemCount(): Int = projectList.size
}

