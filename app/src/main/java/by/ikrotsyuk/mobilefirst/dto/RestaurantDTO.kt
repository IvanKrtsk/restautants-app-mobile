package by.ikrotsyuk.mobilefirst.dto

data class RestaurantDTO (
    val key: String = "",
    val name: String = "",
    val address: String = "",
    val kitchenType: String = "",
    val photoLinks: String = ""/*MutableList<String> = mutableListOf()*/,
)