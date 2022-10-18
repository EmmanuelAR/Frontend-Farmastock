package cr.una.example.frontend_farmastock.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import cr.una.example.frontend_farmastock.R
import cr.una.example.frontend_farmastock.adapter.MedicineAdapter.Companion.MEDICINE_ID
import cr.una.example.frontend_farmastock.databinding.FragmentMedicineDetailsBinding
import cr.una.example.frontend_farmastock.viewmodel.StateMedicine
import cr.una.example.frontend_farmastock.viewmodel.MedicineViewModel

class MedicineDetailsFragment : Fragment() {

    // Definition of the binding variable
    private var _binding: FragmentMedicineDetailsBinding? = null
    private val binding get() = _binding!!

    // Shared view model
    private val medicineViewModel: MedicineViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMedicineDetailsBinding.inflate(inflater, container, false)

        val medicineId: String = arguments?.getString(MEDICINE_ID) ?: "0"

        // Observer method to bind data of task into text views
        medicineViewModel.state.observe(viewLifecycleOwner) { state ->
            // this lets us avoid repeating 'binding.frameNews' before everything
            with(binding.root) {
                when (state) {
                    // just checking equality because Loading is a -singleton object instance-
                    StateMedicine.Loading -> {
                        // TODO: If you need do something in loading
                    }
                    // Error and Success are both -classes- so we need to check their type with 'is'
                    is StateMedicine.Error -> {
                        // TODO: If you need do something in error
                    }
                    is StateMedicine.Success -> {
                        state.medicine?.let {
                            binding.name.text = it.name.toString()
                            binding.dose.text = it.dose.toString()
                            binding.txtQuantity.text = it.quantity.toString()
                        }
                    }
                    else -> {
                        // TODO: Not state loaded
                    }
                }
            }
        }

        binding.btnDelete.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(requireActivity())
            dialogBuilder.setMessage("Are you sure?")
                // if the dialog is cancelable
                .setCancelable(true)
                .setPositiveButton("YES") { dialog, _ ->
                    medicineViewModel.deleteMedicineById(id.toLong())

                    findNavController().navigate(R.id.medicineMainFragment2)

                    dialog.dismiss()

                }
                .setNegativeButton("NO") { dialog, _ ->
                    dialog.dismiss()

                }
            val alert = dialogBuilder.create()
            alert.setTitle("Delete Vehicle")
            alert.show()
        }

        binding.btnUpdate.setOnClickListener {
            val bundle = bundleOf(MEDICINE_ID to id)

            findNavController().navigate(R.id.action_medicineDetailsFragment_to_medicineUpdateFragment, bundle)
        }

        medicineViewModel.getMedicine(id.toLong())
        // Inflate the layout for this fragment
        return binding.root

    }
}