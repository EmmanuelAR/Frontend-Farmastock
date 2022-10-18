package cr.una.example.frontend_farmastock.repository

import cr.una.example.frontend_farmastock.model.UserRequest
import cr.una.example.frontend_farmastock.service.UserService

class UserRepository constructor(
    private val userService: UserService
){
    suspend fun getAllUsers() = userService.getAllUsers()

    suspend fun getUserById(id : Long) = userService.getUserById(id)

    suspend fun deleteUserById(id : Long) = userService.deleteUserById(id)

    suspend fun createUser(userRequest: UserRequest) = userService.createUser(userRequest)

    suspend fun updateUser(userRequest: UserRequest) = userService.updateUser(userRequest)


}