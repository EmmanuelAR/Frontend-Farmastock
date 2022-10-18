package cr.una.example.frontend_farmastock.model

import java.util.*

data class MedicineResponse(
    var id: Long? = null,
    var name: String? = null,
    var dose: Int? = null,
    var quantity: Int? = null,
    //var reminderList: List<ReminderDetails>?=null,
)

data class MedicineRequest(
    var id: Long? = null,
    var name: String? = null,
    var dose: Int? = null,
    var quantity: Int? = null
)