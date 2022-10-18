package cr.una.example.frontend_car_service_app.service

import cr.una.example.frontend_farmastock.model.MedicineRequest
import cr.una.example.frontend_farmastock.model.MedicineResponse
import cr.una.example.frontend_farmastock.service.ServiceBuilder
import retrofit2.Response
import retrofit2.http.*

interface MedicineService {
    @GET("v1/medicines")
    suspend fun getAllMedicines() : Response<List<MedicineResponse>>

    @GET("v1/medicines/{id}")
    suspend fun getMedicineById(@Path("id") id: Long) : Response<MedicineResponse>

    @DELETE("v1/medicines/{id}")
    suspend fun deleteMedicineById(@Path("id") id: Long): Response<Void>

    @POST("v1/medicines")
    suspend fun createMedicine(@Body medicineRequest: MedicineRequest) : Response<MedicineResponse>

    @PUT("v1/medicines")
    suspend fun updateMedicine(@Body medicineRequest: MedicineRequest) : Response<MedicineResponse>

    /*
     * Function or any member of the class that can be called without having the instance of the
     * class then you can write the same as a member of a companion object inside the class
     */
    companion object{
        private var medicineService : MedicineService? = null
        fun getInstance() : MedicineService {
            if (medicineService == null) {
                medicineService = ServiceBuilder.buildService(MedicineService::class.java)
            }
            return medicineService!!
        }
    }

}