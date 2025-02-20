package by.ikrotsyuk.mobilefirst.ui.nav_menu

import by.ikrotsyuk.mobilefirst.R

sealed class NavigationMenuItem(
    val title: String,
    val iconID: Int,
    val route: String
) {
    object RestaurantsList: NavigationMenuItem(
        title = "Restaurants",
        iconID = R.drawable.ic_restaurants,
        route = "restaurants"
    )

    object Favourites: NavigationMenuItem(
        title = "Favourite",
        iconID = R.drawable.ic_favourite,
        route = "favourite"
    )

    object Profile: NavigationMenuItem(
        title = "Profile",
        iconID = R.drawable.ic_profile,
        route = "profile"
    )
}