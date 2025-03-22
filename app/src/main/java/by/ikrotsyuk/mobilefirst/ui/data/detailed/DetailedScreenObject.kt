package by.ikrotsyuk.mobilefirst.ui.data.detailed

import kotlinx.serialization.Serializable

@Serializable
data class DetailedScreenObject(
    val name: String = "",
    val address: String = "",
    val avgBill: String = "",
    val kitchenType: String = "",
    val photoLinks: MutableList<String> = mutableListOf(),
    val isFavourite: Boolean = false
)