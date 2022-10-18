package cr.una.example.frontend_farmastock.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import cr.una.example.frontend_farmastock.R
import cr.una.example.frontend_farmastock.adapter.MedicineAdapter
import cr.una.example.frontend_farmastock.databinding.FragmentMedicineUpdateBinding
import cr.una.example.frontend_farmastock.model.MedicineRequest
import cr.una.example.frontend_farmastock.model.MedicineResponse
import cr.una.example.frontend_farmastock.viewmodel.StateMedicine
import cr.una.example.frontend_farmastock.viewmodel.MedicineViewModel

class MedicineUpdateFragment : Fragment() {

    // Definition of the binding variable
    private var _binding: FragmentMedicineUpdateBinding? = null
    private val binding get() = _binding!!

    // Late init var
    private lateinit var medicineResponse: MedicineResponse
    private lateinit var medicineId: String


    // Shared view model
    private val medicineViewModel: MedicineViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        _binding = FragmentMedicineUpdateBinding.inflate(inflater, container, false)
        medicineId = arguments?.getString(MedicineAdapter.MEDICINE_ID) ?: "0"

        // Observer method to bind data of task into text views
        medicineViewModel.state.observe(viewLifecycleOwner) { state ->
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
                            medicineResponse = it
                            binding.formNameUpdate.setText(it.name.toString())
                            binding.formDoseUpdate.setText(it.dose.toString())
                            binding.formQuantityUpdate.setText(it.quantity.toString())
                        }

                    }
                    else -> {
                        // TODO: Not state loaded
                    }
                }
            }
        }

        binding.btnUpdate.setOnClickListener {

            val medicineUpdated = MedicineRequest(
                id = medicineResponse.id,
                name =  binding.formNameUpdate.text.toString(),
                dose = Integer.parseInt(binding.formDoseUpdate.text.toString()),
                quantity = Integer.parseInt(binding.formQuantityUpdate.text.toString())
            )

            medicineViewModel.updateMedicine(medicineUpdated)
            medicineViewModel.state.observe(viewLifecycleOwner) { state ->
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
                        findNavController().navigate(R.id.action_medicineUpdateFragment_to_medicineMainFragment)
                    }
                    else -> {
                        // TODO: Not state loaded
                    }
                }
            }
        }



        return binding.root



    }


}