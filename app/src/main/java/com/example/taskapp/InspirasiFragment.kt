import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskapp.R


class InspirasiFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_inspirasi, container, false)

        val inspirasiList = listOf(
            Inspirasi("Ginanjar Shomat", "@ginanjar13", "Lorem ipsum dolor sit amet...", 80, 128),
            Inspirasi("Ginanjar Shomat", "@ginanjar12", "Lorem ipsum dolor sit amet...", 65, 95),
            Inspirasi("Ginanjar Shomat", "@ginanjar12", "Lorem ipsum dolor sit amet...", 45, 78),
            Inspirasi("Ginanjar Shomat", "@ginanjar12", "Lorem ipsum dolor sit amet...", 69, 78)
        )

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = InspirasiAdapter(inspirasiList)

        return view
    }
}
