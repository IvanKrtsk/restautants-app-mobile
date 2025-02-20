package by.ikrotsyuk.mobilefirst.ui.nav_menu

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource

@Composable
fun NavigationMenu() {
    val items = listOf(
        NavigationMenuItem.RestaurantsList,
        NavigationMenuItem.Favourites,
        NavigationMenuItem.Profile
    )

    val selectedPage = remember { mutableStateOf("Restaurants") }

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = selectedPage.value == item.title,
                onClick = {
                    selectedPage.value = item.title
                },
                icon = {
                    Icon(painter = painterResource(id = item.iconID), contentDescription = item.title)
                },
                label = {
                    Text(text = item.title)
                }
            )
        }
    }
}