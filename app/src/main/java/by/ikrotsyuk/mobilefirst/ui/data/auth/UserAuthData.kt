package by.ikrotsyuk.mobilefirst.ui.data.auth

import kotlinx.serialization.Serializable

@Serializable
data class UserAuthData(
    val uid: String = "",
    val email : String = ""
)
