package cr.una.example.frontend_farmastock.repository

import cr.una.example.frontend_farmastock.model.MedicineRequest
import cr.una.example.frontend_farmastock.service.MedicineService

class MedicineRepository constructor (private val medicineService: MedicineService){
    init {    }
    suspend fun getAllMedicines() = medicineService.getAllMedicines()
    suspend fun createMedicine(medicineRequest: MedicineRequest) = medicineService.createMedicine(medicineRequest)
}