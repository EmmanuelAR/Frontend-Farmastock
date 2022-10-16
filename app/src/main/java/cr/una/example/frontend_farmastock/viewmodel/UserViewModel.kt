package cr.una.example.frontend_farmastock.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cr.una.example.frontend_farmastock.model.UserRequest
import cr.una.example.frontend_farmastock.model.UserResponse
import cr.una.example.frontend_farmastock.repository.UserRepository
import kotlinx.coroutines.*

sealed class StateUser {
    object Loading : StateUser()
    data class Success(val user: UserResponse?) : StateUser()
    data class SuccessDelete(val deleted: Boolean?) : StateUser()
    data class SuccessList(val userList: List<UserRequest>?) : StateUser()
    data class Error(val message: String) : StateUser()
}

class UserViewModel constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    // this is just a way to keep the mutable LiveData private, so it can't be updated
    private val _state = MutableLiveData<StateUser>()
    val state: LiveData<StateUser> get() = _state

    private var job: Job? = null
    private val errorMessage = MutableLiveData<String>()
    private val loading = MutableLiveData<Boolean>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    /**
     * When we call getTask() suspend method, then it suspends our coroutine.
     * The coroutine on the main thread will be resumed with the result as soon as the
     * withContext block is complete.
     */
    fun getUser(id: Long) {
        _state.value = StateUser.Loading
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = userRepository.getUserById(id)
            withContext(Dispatchers.Main) {
                // if you're using postValue I don't think you need to switch to Dispatchers.Main?
                _state.postValue(
                    // when you get a response, the state is now either Success or Error
                    if (response.isSuccessful) StateUser.Success(response.body())
                    else StateUser.Error("Error : ${response.message()} ")
                )
            }
        }
    }


    /**
     * When we call findAllTask() suspend method, then it suspends our coroutine.
     * The coroutine on the main thread will be resumed with the result as soon as the
     * withContext block is complete.
     */
    fun findAllUser() {
        _state.value = StateUser.Loading
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = userRepository.getAllUsers()
            withContext(Dispatchers.Main) {
                // if you're using postValue I don't think you need to switch to Dispatchers.Main?
                _state.postValue(
                    // when you get a response, the state is now either Success or Error
                    if (response.isSuccessful) StateUser.SuccessList(response.body())
                    else StateUser.Error("Error : ${response.message()} ")
                )
            }
        }
    }

    fun deleteUserById(id: Long) {
        _state.value = StateUser.Loading
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = userRepository.deleteUserById(id)
            withContext(Dispatchers.Main) {
                // if you're using postValue I don't think you need to switch to Dispatchers.Main?
                _state.postValue(
                    // when you get a response, the state is now either Success or Error
                    if (response.isSuccessful) StateUser.SuccessDelete(true)
                    else StateUser.Error("Error : ${response.message()} ")
                )
            }
        }
    }

    fun createUser(userRequest: UserRequest) {
        _state.value = StateUser.Loading
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = userRepository.createUser(userRequest)
            withContext(Dispatchers.Main) {
                // if you're using postValue I don't think you need to switch to Dispatchers.Main?
                _state.postValue(
                    // when you get a response, the state is now either Success or Error
                    (if (response.isSuccessful) {
                        StateUser.Success(response.body() as UserResponse)
                    } else {
                        StateUser.Error("Error : ${response.message()} ")
                        onError("Error : ${response.message()}")
                    }) as StateUser?
                )
            }
        }
    }

    fun updateUser(userRequest: UserRequest) {
        _state.value = StateUser.Loading
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = userRepository.updateUser(userRequest)
            withContext(Dispatchers.Main) {
                // if you're using postValue I don't think you need to switch to Dispatchers.Main?
                _state.postValue(
                    // when you get a response, the state is now either Success or Error
                    if (response.isSuccessful) StateUser.Success(response.body())
                    else StateUser.Error("Error : ${response.message()} ")
                )
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}