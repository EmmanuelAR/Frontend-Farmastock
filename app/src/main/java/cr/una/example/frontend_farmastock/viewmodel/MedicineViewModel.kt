package cr.una.example.frontend_farmastock.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cr.una.example.frontend_farmastock.model.MedicineRequest
import cr.una.example.frontend_farmastock.model.MedicineResponse
import cr.una.example.frontend_farmastock.repository.MedicineRepository
import kotlinx.coroutines.*

sealed class StateMedicine {
    object Loading : StateMedicine()
    data class Success(val medicine: MedicineResponse?) : StateMedicine()
    data class SuccessDelete(val deleted: Boolean?) : StateMedicine()
    data class SuccessList(val medicineList: List<MedicineRequest>?) : StateMedicine()
    data class Error(val message: String) : StateMedicine()
}

class MedicineViewModel constructor(
    private val medicineRepository: MedicineRepository,
) : ViewModel() {

    // this is just a way to keep the mutable LiveData private, so it can't be updated
    private val _state = MutableLiveData<StateMedicine>()
    public val state: LiveData<StateMedicine> get() = _state

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
    fun getAllMedicines() {
        _state.value = StateMedicine.Loading
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = medicineRepository.getAllMedicines()
            withContext(Dispatchers.Main) {
                // if you're using postValue I don't think you need to switch to Dispatchers.Main?
                _state.postValue(
                    // when you get a response, the state is now either Success or Error
                    if (response.isSuccessful) {
                        StateMedicine.SuccessList(response.body())
                    }
                    else StateMedicine.Error("Error : ${response.message()} ")
                )
            }
        }
    }

    fun createMedicine(medicineRequest: MedicineRequest) {
        _state.value = StateMedicine.Loading
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = medicineRepository.createMedicine(medicineRequest)
            withContext(Dispatchers.Main) {
                // if you're using postValue I don't think you need to switch to Dispatchers.Main?
                _state.postValue(
                    // when you get a response, the state is now either Success or Error
                    (if (response.isSuccessful) {
                        StateMedicine.Success(response.body() as MedicineResponse)
                    } else {
                        StateMedicine.Error("Error : ${response.message()} ")
                        onError("Error : ${response.message()}")
                    }) as StateMedicine?
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