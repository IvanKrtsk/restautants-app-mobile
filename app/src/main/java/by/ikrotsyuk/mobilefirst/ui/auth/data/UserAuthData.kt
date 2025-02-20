package by.ikrotsyuk.mobilefirst.ui.auth.data

import kotlinx.serialization.Serializable

@Serializable
data class UserAuthData(
    val uid: String = "",
    val email : String = ""
)
