import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taskapp.databinding.FragmentCustomDaysBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CustomDaysBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentCustomDaysBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomDaysBottomSheetBinding.inflate(inflater, container, false)

        // Tambahkan logika untuk mengelola pilihan hari

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
