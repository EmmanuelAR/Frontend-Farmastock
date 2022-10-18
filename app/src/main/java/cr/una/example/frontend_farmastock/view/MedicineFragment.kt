package cr.una.example.frontend_farmastock.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cr.una.example.frontend_farmastock.R
import cr.una.example.frontend_farmastock.databinding.FragmentMedicinesListBinding
import cr.una.example.frontend_farmastock.model.MedicineRequest
import cr.una.example.frontend_farmastock.utils.MyApplication
import cr.una.example.frontend_farmastock.viewmodel.MedicineViewModel
import cr.una.example.frontend_farmastock.viewmodel.MedicineViewModelFactory
import cr.una.example.frontend_farmastock.viewmodel.StateMedicine
import kotlinx.android.synthetic.main.fragment_add_medicine.view.*
import kotlinx.android.synthetic.main.fragment_medicines_list.view.*


class MedicineFragment : Fragment(), MedicineRecyclerViewInterface {

    private var _binding: FragmentMedicinesListBinding? = null
    private val binding get() = _binding!!

    private val medicineViewModel: MedicineViewModel by activityViewModels {
        MedicineViewModelFactory()
    }

    private lateinit var viewModel: MedicineViewModel
    var strings = ArrayList<String>()
    lateinit var recylcerViewMedicinesList : RecyclerView
    private lateinit var medicineResponse: List<MedicineRequest>

    val model: MedicineViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMedicinesListBinding.inflate(inflater, container, false)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMedicinesListBinding.bind(view)
        binding.addMedicineBtn.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_medicineFragment_to_addMedicineFragment)
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val id = MyApplication.sessionManager?.fetchUserIdLogged()

        medicineViewModel.getAllMedicines()
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

                  is StateMedicine.SuccessList -> {
                      state.medicineList?.let {
                          medicineResponse = it
                          for (item in medicineResponse){
                              item.name?.let { it1 -> strings.add(it1) }
                          }
                      }

                  }
                  else -> {
                      // TODO: Not state loaded
                  }
              }
          }
            var adapter = MedicinesRecyclerViewAdapter(strings)

            recylcerViewMedicinesList = view?.findViewById(R.id.recyclerview)!!
            recylcerViewMedicinesList.adapter = adapter
            recylcerViewMedicinesList.layoutManager = LinearLayoutManager(view!!.context)
        }


    }

    override fun onItemClick(position: Int) {

    }

}
