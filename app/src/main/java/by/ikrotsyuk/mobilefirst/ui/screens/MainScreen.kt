package by.ikrotsyuk.mobilefirst.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import by.ikrotsyuk.mobilefirst.dto.RestaurantDTO
import by.ikrotsyuk.mobilefirst.ui.components.RestaurantListItem
import by.ikrotsyuk.mobilefirst.ui.nav_menu.NavigationMenu
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

@Composable
fun MainScreen() {

    val restaurantsListState = remember {
        mutableStateOf(emptyList<RestaurantDTO>())
    }

    LaunchedEffect(Unit) {
        val db = Firebase.firestore
        getRestaurants(db){ restaurants ->
            restaurantsListState.value = restaurants
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { NavigationMenu() }
    ) { paddingValue ->
        LazyColumn(
            modifier = Modifier.padding(paddingValue)
        ) {
            items(restaurantsListState.value){ it ->
                RestaurantListItem(it)
            }
            /*item {
                RestaurantListItem(
                    RestaurantDTO(
                        "awda",
                        "myata",
                        "zyba",
                        "kalik",
                        "https://koko.by/storage/thumbs/place_images/4122/w1600_h1600_q90_aspect_ratio_1_water_016838148561683814856.jpg"
                    )
                )
            }
            item {
                RestaurantListItem(
                    RestaurantDTO(
                        "awda", "chaynyi pjianitsa",
                        "internatsionalnaya",
                        "kalyan",
                        "https://koko.by/storage/thumbs/place_images/3817/w1600_h1600_q90_aspect_ratio_1_water_216788265231678826523.jpg"
                    )
                )
            }*/
        }
    }
}

private fun getRestaurants(
    db: FirebaseFirestore,
    onRestaurants: (List<RestaurantDTO>) -> Unit
){
    db.collection("restaurants")
        .get()
        .addOnSuccessListener { task ->
            onRestaurants(task.toObjects(RestaurantDTO::class.java))
        }
}