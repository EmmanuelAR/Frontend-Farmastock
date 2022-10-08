package cr.una.example.frontend_farmastock.model

data class RoleDetails(
    var id: Long? = null,
    var name: String? = null,
    var privilegeList: List<PrivilegeDetails>? = null,
)