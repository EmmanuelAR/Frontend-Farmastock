package cr.una.example.frontend_farmastock.model

import java.util.*

data class ReminderResponse(
    var id: Long? = null,
    var createDate: Date? = null, // Date(year: Int, month: Int, day: Int, hour: Int, minute: Int)
    var description: String? = null,
    var user: UserResponse? = null,
    var medicine: MedicineResponse? = null
)
data class ReminderRequest(
    var id: Long? = null,
    var createDate: String? = null, // Date(year: Int, month: Int, day: Int, hour: Int, minute: Int)
    var description: String? = null,
    var user: UserResponse? = null,
    var medicine: MedicineResponse? = null
)