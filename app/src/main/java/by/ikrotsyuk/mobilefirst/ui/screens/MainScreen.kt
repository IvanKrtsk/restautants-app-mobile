package by.ikrotsyuk.mobilefirst.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import by.ikrotsyuk.mobilefirst.dto.RestaurantDTO
import by.ikrotsyuk.mobilefirst.ui.components.RestaurantListItem
import by.ikrotsyuk.mobilefirst.ui.nav_menu.NavigationMenu
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {

    val firestore = remember {
        Firebase.firestore
    }

    val restaurants = firestore.collection("restaurants").get()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { NavigationMenu() }
    ) {
        LazyColumn(
            modifier = Modifier.padding(top = 15.dp)
        ) {
            item {
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
            }
        }
    }
}