package cr.una.example.frontend_farmastock.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cr.una.example.frontend_farmastock.R
import cr.una.example.frontend_farmastock.databinding.FragmentAddMedicineBinding
import cr.una.example.frontend_farmastock.databinding.FragmentMedicinesListBinding
import cr.una.example.frontend_farmastock.model.MedicineRequest
import cr.una.example.frontend_farmastock.utils.MyApplication
import cr.una.example.frontend_farmastock.viewmodel.MedicineViewModel
import cr.una.example.frontend_farmastock.viewmodel.MedicineViewModelFactory

class AddMedicineFragment : Fragment() {
    // Definition of the binding variable
    private var _binding: FragmentAddMedicineBinding? = null
    private val binding get() = _binding!!

    // View model
    private val medicineViewModel: MedicineViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddMedicineBinding.inflate(inflater, container, false)

        binding.saveMedicine.setOnClickListener {

            medicineViewModel.createMedicine(
                MedicineRequest(
                    id = 0,
                    name = binding.nombreMedicina.text.toString(),
                    dose = Integer.parseInt(binding.dosisMedicina.text.toString()),
                    quantity = Integer.parseInt(binding.cantidadMedicina.text.toString())
                    )
            )

            findNavController().navigate(R.id.action_addMedicineFragment_to_medicineFragment)
        }
        // Inflate the layout for this fragment
        return binding.root
    }

}