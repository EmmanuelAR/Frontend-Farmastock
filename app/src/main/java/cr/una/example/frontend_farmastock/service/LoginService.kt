package cr.una.example.frontend_farmastock.service


import cr.una.example.frontend_farmastock.model.LoginRequest
import cr.una.example.frontend_farmastock.model.UserLoginResponse
import cr.una.example.frontend_farmastock.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LoginService {
    @POST("/v1/users/login")
    suspend fun login(@Body userLogin: LoginRequest) : Response<UserLoginResponse>

    @GET("v1/users/email/{username}")
    suspend fun getUserByEmail(@Path("username") username: String) : Response<UserResponse>

    companion object {
        private var loginService : LoginService? = null
        fun getInstance() : LoginService {
            if (loginService == null) {
                loginService = ServiceBuilder.buildService(LoginService::class.java)
            }
            return loginService!!
        }
    }
}