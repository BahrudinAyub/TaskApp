import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskapp.R


class InspirasiAdapter(private val inspirasiList: List<Inspirasi>) :
    RecyclerView.Adapter<InspirasiAdapter.InspirasiViewHolder>() {

    class InspirasiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUsername: TextView = itemView.findViewById(R.id.tvUsername)
        val tvUserHandle: TextView = itemView.findViewById(R.id.tvUserHandle)
        val tvContent: TextView = itemView.findViewById(R.id.tvContent)
        val tvLikes: TextView = itemView.findViewById(R.id.tvLikes)
        val tvViews: TextView = itemView.findViewById(R.id.tvViews)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InspirasiViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_inspirasi, parent, false)
        return InspirasiViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: InspirasiViewHolder, position: Int) {
        val currentItem = inspirasiList[position]

        holder.tvUsername.text = currentItem.username
        holder.tvUserHandle.text = currentItem.userHandle
        holder.tvContent.text = currentItem.content
        holder.tvLikes.text = currentItem.likes.toString()
        holder.tvViews.text = currentItem.views.toString()
    }

    override fun getItemCount() = inspirasiList.size
}
