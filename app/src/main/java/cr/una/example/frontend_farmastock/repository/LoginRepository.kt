package cr.una.example.frontend_farmastock.repository

import cr.una.example.frontend_farmastock.model.LoginRequest
import cr.una.example.frontend_farmastock.model.UserLoginResponse
import cr.una.example.frontend_farmastock.service.LoginService
import cr.una.example.frontend_farmastock.utils.MyApplication.Companion.sessionManager
import retrofit2.Response

class LoginRepository constructor (

    private val loginService: LoginService,

    ){

    // in-memory cache of the loggedInUser object
    private var user: UserLoginResponse? = null

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        sessionManager?.deleteAuthToken()
        sessionManager?.deleteUserIdLogged()
    }

    suspend fun login(userLogin: LoginRequest)  : Response<UserLoginResponse> {
        val response = loginService.login(userLogin)

        if (response.isSuccessful) {
            setLoggedInUser(response.body(), response.headers()["Authorization"].toString())
            setIdByEmail(response.body())
        }

        return response
    }

    private fun setLoggedInUser(loginRequest: UserLoginResponse?, token:String) {
        this.user = loginRequest
        sessionManager?.saveAuthToken(token)
    }

    suspend fun getUserByEmail(username : String) = loginService.getUserByEmail(username)
    suspend fun setIdByEmail(loginRequest: UserLoginResponse?){
        var response = loginRequest?.let { getUserByEmail(it.username) }
        if (response != null) {
            response.body()?.id?.let { sessionManager?.saveUserIdLogged(it) }
        }
    }
}