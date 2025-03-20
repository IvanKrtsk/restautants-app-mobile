package by.ikrotsyuk.mobilefirst.ui.data.bottomNavigation

import kotlinx.serialization.Serializable

@Serializable
data class AppNavObject(
    val title: String = "",
    val uid: String = "",
    val email : String = ""
)