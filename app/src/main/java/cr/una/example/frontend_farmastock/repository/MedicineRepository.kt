package cr.una.example.frontend_farmastock.repository

import cr.una.example.frontend_car_service_app.service.MedicineService
import cr.una.example.frontend_farmastock.model.MedicineRequest


class MedicineRepository constructor(
    private val medicineService: MedicineService
){
    suspend fun getAllMedicines() = medicineService.getAllMedicines()

    suspend fun getMedicineById(id : Long) = medicineService.getMedicineById(id)

    suspend fun deleteMedicineById(id : Long) = medicineService.deleteMedicineById(id)

    suspend fun createMedicine(medicineRequest: MedicineRequest) = medicineService.createMedicine(medicineRequest)

    suspend fun updateMedicine(medicineRequest: MedicineRequest) = medicineService.updateMedicine(medicineRequest)
}