package cr.una.example.frontend_farmastock.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cr.una.example.frontend_farmastock.repository.ReminderRepository
import cr.una.example.frontend_farmastock.service.ReminderService

@Suppress("UNCHECKED_CAST")
class ReminderViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ReminderViewModel::class.java)) {
            ReminderViewModel(
                reminderRepository = ReminderRepository(
                    reminderService = ReminderService.getInstance()
                )
            ) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}