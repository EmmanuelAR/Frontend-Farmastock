package cr.una.example.frontend_farmastock.service

import cr.una.example.frontend_farmastock.model.ReminderRequest
import cr.una.example.frontend_farmastock.model.ReminderResponse
import retrofit2.Response
import retrofit2.http.*

interface ReminderService {
    @GET("v1/reminders")
    suspend fun getAllReminders() : Response<List<ReminderResponse>>

    @GET("v1/reminders/{id}")
    suspend fun getReminderById(@Path("id") id: Long) : Response<ReminderResponse>

    @DELETE("v1/reminders/{id}")
    suspend fun deleteReminderById(@Path("id") id: Long): Response<Void>

    @POST("v1/reminders")
    suspend fun createReminder(@Body reminderRequest: ReminderRequest) : Response<ReminderResponse>

    @PUT("v1/reminders")
    suspend fun updateReminder(@Body reminderRequest: ReminderRequest) : Response<ReminderResponse>

    /*
     * Function or any member of the class that can be called without having the instance of the
     * class then you can write the same as a member of a companion object inside the class
     */
    companion object{
        private var reminderService : ReminderService? = null
        fun getInstance() : ReminderService {
            if (reminderService == null) {
                reminderService = ServiceBuilder.buildService(ReminderService::class.java)
            }
            return reminderService!!
        }
    }

}