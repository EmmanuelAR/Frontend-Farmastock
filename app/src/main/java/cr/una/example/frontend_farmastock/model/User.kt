package cr.una.example.frontend_farmastock.model

import java.util.*

data class UserResponse(
    var id: Long? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var password: String? = null,
    var enabled: Boolean? = null,
    var tokenExpired: Boolean? = null,
    var createDate: Date? = null,
    var roleList: List<RoleDetails>?=null,
)

data class UserRequest(
    var id: Long? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var password: String? = null,
    var enabled: Boolean? = null,
    var roleList: List<RoleDetails>? = null,
    var createDate: Date?=null,
)