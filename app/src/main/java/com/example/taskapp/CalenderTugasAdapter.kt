import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.taskapp.R
import java.text.SimpleDateFormat
import java.util.*

class CalendarTugasAdapter(
    private val days: List<Date?>, // List of dates (null for empty days)
    private val onItemClickListener: (Date) -> Unit // Callback for when a date is clicked
) : RecyclerView.Adapter<CalendarTugasAdapter.ViewHolder>() {

    private var selectedPosition: Int = -1 // Track the selected position

    // ViewHolder class for the RecyclerView
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dayText: TextView = view.findViewById(R.id.dayText)
        val dayOfWeekText: TextView = view.findViewById(R.id.dayOfWeekText)
        val itemLayout: View = view.findViewById(R.id.itemLayout) // Reference to the parent layout to change background colour
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.calendar_day_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date = days[position]

        if (date != null) {
            // Display the date
            holder.dayText.text = SimpleDateFormat("d", Locale.getDefault()).format(date)
            // Display the day of the week (e.g., Mon, Tue)
            holder.dayOfWeekText.text = SimpleDateFormat("EEE", Locale.getDefault()).format(date)

            // Check if this position is selected
            if (position == selectedPosition) {
                // Change background to selected state (e.g., blue background with white text)
                holder.itemLayout.setBackgroundResource(R.drawable.selected_background) // Drawable for selected state
                holder.dayText.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.white))
                holder.dayOfWeekText.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.white))
            } else {
                // Default background for non-selected state
                holder.itemLayout.setBackgroundResource(R.drawable.default_background) // Drawable for default state
                holder.dayText.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.black))
                holder.dayOfWeekText.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.darker_gray))
            }
        } else {
            // Placeholder for empty days (null dates)
            holder.dayText.text = ""
            holder.dayOfWeekText.text = ""
        }

        // Handle item click
        holder.itemView.setOnClickListener {
            date?.let {
                onItemClickListener(it)
                selectedPosition = position // Update selected position
                notifyDataSetChanged() // Notify the adapter to refresh views
            }
        }
    }

    override fun getItemCount(): Int {
        return days.size
    }
}
