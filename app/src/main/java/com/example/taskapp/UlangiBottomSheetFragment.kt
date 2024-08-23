import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taskapp.databinding.FragmentUlangiBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class UlangiBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentUlangiBottomSheetBinding? = null
    private val binding get() = _binding!!

    var onSelectionMade: ((String) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUlangiBottomSheetBinding.inflate(inflater, container, false)

        // Logika untuk menutup BottomSheet
        binding.closeButtonUlangi.setOnClickListener {
            dismiss()
        }

        // Menambahkan logika ketika checkbox_custom diklik
        binding.checkboxCustom.setOnClickListener {
            // Contoh: Menampilkan BottomSheet baru atau melakukan tindakan lain
            showNewBottomSheet()
        }

        // Menangani pemilihan ulangi
        binding.checkboxSekali.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                onSelectionMade?.invoke("Sekali")
                dismiss()
            }
        }

        binding.checkboxSetiapHari.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                onSelectionMade?.invoke("Setiap Hari")
                dismiss()
            }
        }

        binding.checkboxSeninJumat.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                onSelectionMade?.invoke("Senin sampai Jum'at")
                dismiss()
            }
        }

        binding.checkboxSabtuMinggu.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                onSelectionMade?.invoke("Sabtu dan Minggu")
                dismiss()
            }
        }

        binding.checkboxCustom.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                onSelectionMade?.invoke("Custom")
                dismiss()
            }
        }



        return binding.root
    }

    private fun showNewBottomSheet() {
        // Buat instance dari bottom sheet yang baru
        val newBottomSheet = CustomDaysBottomSheetFragment()

        // Tampilkan bottom sheet
        newBottomSheet.show(parentFragmentManager, newBottomSheet.tag)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

