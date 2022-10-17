package cr.una.example.frontend_farmastock.service

import cr.una.example.frontend_farmastock.model.MedicineRequest
import cr.una.example.frontend_farmastock.model.MedicineResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MedicineService {
   // @POST("/v1/users/login")
   // suspend fun login(@Body userLogin: LoginRequest) : Response<UserLoginResponse>

    @GET("v1/medicines")
    suspend fun getAllMedicines() : Response<List<MedicineRequest>>

    @POST("v1/medicines")
    suspend fun createMedicine(@Body medicineRequest: MedicineRequest) : Response<MedicineResponse>

    companion object {
        private var medicineService : MedicineService? = null
        fun getInstance() : MedicineService {
            if (medicineService == null) {
                medicineService = ServiceBuilder.buildService(MedicineService::class.java)
            }
            return medicineService!!
        }
    }
}