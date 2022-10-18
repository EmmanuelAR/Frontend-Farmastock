package cr.una.example.frontend_farmastock.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cr.una.example.frontend_farmastock.repository.LoginRepository
import cr.una.example.frontend_farmastock.repository.MedicineRepository
import cr.una.example.frontend_farmastock.service.LoginService
import cr.una.example.frontend_farmastock.service.MedicineService

@Suppress("UNCHECKED_CAST")
class MedicineViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MedicineViewModel::class.java)) {
            MedicineViewModel (
                medicineRepository = MedicineRepository(
                    medicineService = MedicineService.getInstance()
                )
            ) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}