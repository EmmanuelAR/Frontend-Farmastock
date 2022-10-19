package cr.una.example.frontend_farmastock.service

import cr.una.example.frontend_farmastock.model.UserRequest
import cr.una.example.frontend_farmastock.model.UserResponse
import retrofit2.Response
import retrofit2.http.*

interface UserService {
    @GET("v1/users")
    suspend fun getAllUsers() : Response<List<UserResponse>>

    @GET("v1/users/{id}")
    suspend fun getUserById(@Path("id") id: Long) : Response<UserResponse>

    @DELETE("v1/users/{id}")
    suspend fun deleteUserById(@Path("id") id: Long): Response<Void>

    @POST("v1/users")
    suspend fun createUser(@Body UserRequest: UserRequest) : Response<UserResponse>

    @PUT("v1/users")
    suspend fun updateUser(@Body UserRequest: UserRequest) : Response<UserResponse>

    /*
     * Function or any member of the class that can be called without having the instance of the
     * class then you can write the same as a member of a companion object inside the class
     */
    companion object{
        private var userService : UserService? = null
        fun getInstance() : UserService {
            if (userService == null) {
                userService = ServiceBuilder.buildService(UserService::class.java)
            }
            return userService!!
        }
    }
}