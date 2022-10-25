package cr.una.example.frontend_farmastock.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cr.una.example.frontend_farmastock.model.ReminderRequest
import cr.una.example.frontend_farmastock.model.ReminderResponse
import cr.una.example.frontend_farmastock.repository.ReminderRepository
import kotlinx.coroutines.*

sealed class StateReminder {
    object Loading : StateReminder()
    data class Success(val reminderResponse: ReminderResponse?) : StateReminder()
    data class SuccessDelete(val deleted: Boolean?) : StateReminder()
    data class SuccessList(val reminderList: List<ReminderResponse>?) : StateReminder()
    data class Error(val message: String) : StateReminder()
}

class ReminderViewModel constructor(
    private val reminderRepository: ReminderRepository,
) : ViewModel() {

    // this is just a way to keep the mutable LiveData private, so it can't be updated
    private val _state = MutableLiveData<StateReminder>()
    val state: LiveData<StateReminder> get() = _state

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
    fun getReminder(id: Long) {
        _state.value = StateReminder.Loading
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = reminderRepository.getReminderById(id)
            withContext(Dispatchers.Main) {
                // if you're using postValue I don't think you need to switch to Dispatchers.Main?
                _state.postValue(
                    // when you get a response, the state is now either Success or Error
                    if (response.isSuccessful) StateReminder.Success(response.body())
                    else StateReminder.Error("Error : ${response.message()} ")
                )
            }
        }
    }

    /**
     * When we call findAllTask() suspend method, then it suspends our coroutine.
     * The coroutine on the main thread will be resumed with the result as soon as the
     * withContext block is complete.
     */
    fun findAllReminders() {
        _state.value = StateReminder.Loading
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = reminderRepository.getAllReminders()
            withContext(Dispatchers.Main) {
                // if you're using postValue I don't think you need to switch to Dispatchers.Main?
                _state.postValue(
                    // when you get a response, the state is now either Success or Error
                    if (response.isSuccessful) StateReminder.SuccessList(response.body())
                    else StateReminder.Error("Error : ${response.message()} ")
                )
            }
        }
    }

    fun deleteReminderById(id: Long) {
        _state.value = StateReminder.Loading
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = reminderRepository.deleteReminderById(id)
            withContext(Dispatchers.Main) {
                // if you're using postValue I don't think you need to switch to Dispatchers.Main?
                _state.postValue(
                    // when you get a response, the state is now either Success or Error
                    if (response.isSuccessful) StateReminder.SuccessDelete(true)
                    else StateReminder.Error("Error : ${response.message()} ")
                )
            }
        }
    }

    fun createReminder(reminderRequest: ReminderRequest) {
        _state.value = StateReminder.Loading
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = reminderRepository.createReminder(reminderRequest)
            withContext(Dispatchers.Main) {
                // if you're using postValue I don't think you need to switch to Dispatchers.Main?
                _state.postValue(
                    // when you get a response, the state is now either Success or Error
                    (if (response.isSuccessful) {
                        StateReminder.Success(response.body() as ReminderResponse)
                    } else {
                        StateReminder.Error("Error : ${response.message()} ")
                        onError("Error : ${response.message()}")
                    }) as StateReminder?
                )
            }
        }
    }

    fun updateReminder(reminderRequest: ReminderRequest) {
        _state.value = StateReminder.Loading
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = reminderRepository.updateReminder(reminderRequest)
            withContext(Dispatchers.Main) {
                // if you're using postValue I don't think you need to switch to Dispatchers.Main?
                _state.postValue(
                    // when you get a response, the state is now either Success or Error
                    if (response.isSuccessful) StateReminder.Success(response.body())
                    else StateReminder.Error("Error : ${response.message()} ")
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