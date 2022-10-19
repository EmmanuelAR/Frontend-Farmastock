package cr.una.example.frontend_farmastock.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import cr.una.example.frontend_farmastock.R
import cr.una.example.frontend_farmastock.databinding.FragmentMedicineAddBinding
import cr.una.example.frontend_farmastock.databinding.FragmentMedicineMainBinding
import cr.una.example.frontend_farmastock.model.MedicineRequest
import cr.una.example.frontend_farmastock.viewmodel.MedicineViewModel


class MedicineAddFragment : Fragment() {

    // Definition of the binding variable
    private var _binding: FragmentMedicineAddBinding? = null
    private val binding get() = _binding!!

    // View model
    private val medicineViewModel: MedicineViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMedicineAddBinding.inflate(inflater, container, false)


        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMedicineAddBinding.bind(view)
        binding.btnCreate.setOnClickListener {

            medicineViewModel.createMedicine(
                MedicineRequest(
                    id =  8,
                    name = binding.formName.text.toString(),
                    dose = Integer.parseInt(binding.formDose.text.toString()),
                    quantity =  Integer.parseInt(binding.formQuantity.text.toString())

                )
            )

           Navigation.findNavController(view).navigate(R.id.action_medicineAddFragment_to_medicineMainFragment)
        }
    }
}
