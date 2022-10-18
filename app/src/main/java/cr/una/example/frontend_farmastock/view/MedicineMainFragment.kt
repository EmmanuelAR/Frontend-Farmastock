package cr.una.example.frontend_farmastock.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import cr.una.example.frontend_farmastock.R
import cr.una.example.frontend_farmastock.adapter.MedicineAdapter
import cr.una.example.frontend_farmastock.databinding.FragmentMedicineMainBinding
import cr.una.example.frontend_farmastock.viewmodel.MedicineViewModel
import cr.una.example.frontend_farmastock.viewmodel.MedicineViewModelFactory
import cr.una.example.frontend_farmastock.viewmodel.StateMedicine

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class MedicineMainFragment : Fragment() {

    // Definition of the binding variable
    private var _binding: FragmentMedicineMainBinding? = null
    private val binding get() = _binding!!


    private val medicineViewModel : MedicineViewModel by activityViewModels {
        MedicineViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val adapter = MedicineAdapter()
        _binding = FragmentMedicineMainBinding.inflate(inflater, container, false)
        binding.medicineList.adapter = adapter
        binding.medicineList.layoutManager = LinearLayoutManager(activity)
        medicineViewModel.findAllMedicine()
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
                is StateMedicine.SuccessList -> {
                    state.medicineList?.let { adapter.setMedicineList(it) }
                }
                else -> {
                    // TODO: Not state loaded
                }
            }
        }
        // NUEVO
        binding.SearchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })

        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMedicineMainBinding.bind(view)
        binding.buttonMainAddVehicle.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_medicineMainFragment2_to_medicineAddFragment)
        }
    }
}

