package cr.una.example.frontend_farmastock.repository

import cr.una.example.frontend_farmastock.model.ReminderRequest
import cr.una.example.frontend_farmastock.service.ReminderService


class ReminderRepository constructor(
    private val reminderService: ReminderService
){
    suspend fun getAllReminders() = reminderService.getAllReminders()

    suspend fun getReminderById(id : Long) = reminderService.getReminderById(id)

    suspend fun deleteReminderById(id : Long) = reminderService.deleteReminderById(id)

    suspend fun createReminder(reminderRequest: ReminderRequest) = reminderService.createReminder(reminderRequest)

    suspend fun updateReminder(reminderRequest: ReminderRequest) = reminderService.updateReminder(reminderRequest)
}