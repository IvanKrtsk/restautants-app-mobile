package by.ikrotsyuk.mobilefirst.dto

import kotlinx.serialization.Serializable

@Serializable
data class RestaurantDTO (
    val key: String = "",
    val name: String = "",
    val address: String = "",
    val avgBill: String = "",
    val kitchenType: String = "",
    val photoLinks: MutableList<String> = mutableListOf(),
    val isFavourite: Boolean = false
)