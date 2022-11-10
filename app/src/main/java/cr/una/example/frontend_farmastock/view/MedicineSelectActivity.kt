package cr.una.example.frontend_farmastock.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cr.una.example.frontend_farmastock.R
import cr.una.example.frontend_farmastock.adapter.MedicineSelectAdapter
import cr.una.example.frontend_farmastock.databinding.ActivityLoginBinding
import cr.una.example.frontend_farmastock.databinding.ActivityMedicineSelectBinding
import cr.una.example.frontend_farmastock.viewmodel.*

class MedicineSelectActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMedicineSelectBinding
    private lateinit var medicineViewModel: MedicineViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicineSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // LoginViewModelFactory
        medicineViewModel =
            ViewModelProvider(this, MedicineViewModelFactory())[MedicineViewModel::class.java]

        val adapter = MedicineSelectAdapter()
        binding.rvMedicineListSelect.adapter = adapter
        binding.rvMedicineListSelect.layoutManager = LinearLayoutManager(this)
        medicineViewModel.findAllMedicine()
        medicineViewModel.state.observe(this, Observer {state ->
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
                    state.medicineList?.let { adapter.setVehicleList(it) }
                }
                else -> {
                    // TODO: Not state loaded
                }
            }
        })

        binding.SearchViewMedicineSelect.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })
    }
}