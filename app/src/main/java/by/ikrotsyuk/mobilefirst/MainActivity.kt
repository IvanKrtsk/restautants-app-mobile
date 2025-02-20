package by.ikrotsyuk.mobilefirst

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import by.ikrotsyuk.mobilefirst.dto.RestaurantDTO
import by.ikrotsyuk.mobilefirst.ui.auth.data.RegistrationScreenObject
import by.ikrotsyuk.mobilefirst.ui.auth.data.UserAuthData
import by.ikrotsyuk.mobilefirst.ui.screens.MainScreen
import by.ikrotsyuk.mobilefirst.ui.screens.RegistrationScreen
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var fs = Firebase.firestore

        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = RegistrationScreenObject){
                composable<RegistrationScreenObject> {
                    RegistrationScreen { navData ->
                        navController.navigate(navData)
                    }
                }

                composable<UserAuthData>{ navEntry ->
                    val navData = navEntry.toRoute<UserAuthData>()
                    MainScreen()
                }
            }
        }
    }
}

/*saveBook(
                fs,
                "zavtraki",
                "zybitskaya",
                "smeshannaya",
                "https://koko.by/storage/thumbs/place_images/4122/w1600_h1600_q90_aspect_ratio_1_water_016838148561683814856.jpg"
            )
            saveBook(
                fs,
                "chaynyi pjianitsa",
                "internatsionalnaya",
                "kalyan",
                "https://koko.by/storage/thumbs/place_images/3817/w1600_h1600_q90_aspect_ratio_1_water_216788265231678826523.jpg"
            )*/

private fun saveBook(
    firestore: FirebaseFirestore,
    name: String,
    address: String,
    kitchenType: String,
    photoLinks: String,
){
    val db = firestore.collection("restaurants")
    val key = db.document().id
    db.document(key)
        .set(RestaurantDTO(
            key = key,
            name = name,
            address = address,
            kitchenType = kitchenType,
            photoLinks = photoLinks
        ))
}
