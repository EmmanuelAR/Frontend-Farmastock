package cr.una.example.frontend_farmastock.view

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import cr.una.example.frontend_farmastock.R
import cr.una.example.frontend_farmastock.databinding.FragmentMedicineAddBinding
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
            if(validateBlankSpaces()){

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
    fun validateBlankSpaces():Boolean{
        if(TextUtils.isEmpty(binding.formDose.text.toString())|| !validateIfint(binding.formDose.text)){
            binding.formDose.setError("The field dose cannot be empty and must be a number")
            return false
        }
        if(TextUtils.isEmpty(binding.formName.text.toString())){
            binding.formName.setError("Medicine name field  cannot be empty! ")
            return false
        }
        if(TextUtils.isEmpty(binding.formDose.text.toString()) || !validateIfint(binding.formQuantity.text))  {
            binding.formQuantity.setError("The quantity field cannot be empty and must be a number ")
            return false
        }
        var dose = Integer.parseInt(binding.formDose.text.toString())
        var quantity = Integer.parseInt(binding.formQuantity.text.toString())

        if(dose > quantity){
            binding.formDose.setError("The dose cannot be higher than the quantity!")
            return false
        }

        return true
    }
    fun validateIfint(string: Editable):Boolean{
        for(x in string){
            if (!x.isDigit()){
                return false
            }
        }
        return true
    }


}
