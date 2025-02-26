package by.ikrotsyuk.mobilefirst

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
        var db = Firebase.firestore

        /*saveBook(
                db,
                "Madmen",
                "Минск, Интернациональная ул., 25А",
                "35р",
                "Европейская",
                "https://avatars.mds.yandex.net/get-altay/10879408/2a00000190b772379cca0be2a045437d32b6/XXXL"
            )
            saveBook(
                db,
                "Honky Tonk",
                "Минск, площадь Свободы, 23",
                "50р",
                "Европейская, американская",
                "https://avatars.mds.yandex.net/get-altay/14813057/2a000001942cc3f30c61204cdf23dd1ddfda/XXXL"
            )
        saveBook(
            db,
            "Astoria Central",
            "Интернациональная ул., 25А",
            "60р",
            "Европейская, белорусская, японская",
            "https://avatars.mds.yandex.net/get-altay/13325287/2a0000018ef98d1051eb55e354a055a75169/XXXL"
        )
        saveBook(
            db,
            "Гамбринус",
            "Минск, площадь Свободы, 2",
            "45р",
            "Европейская, белорусская, ирландская, немецкая, русская, чешская, английская, славянских народов",
            "https://avatars.mds.yandex.net/get-altay/492546/2a0000015f6c85536e1246d757517aa64cb9/XXXL"
        )
        saveBook(
            db,
            "Marbl",
            "Минск, площадь Свободы, 4",
            "100р",
            "Европейская, итальянская, испанская, греческая, французская, авторская, морская, мясная, рыбная, средиземноморская, вегетарианская, постная",
            "https://avatars.mds.yandex.net/get-altay/14165812/2a00000191c8ab26024ecaf398a304e70f58/XXXL"
        )
        saveBook(
            db,
            "Старый дом",
            "Минск, ул. Кирилла и Мефодия, 6А",
            "40р",
            "Европейская, белорусская, домашняя",
            "https://avatars.mds.yandex.net/get-altay/5098065/2a0000018192246e4c1f318f53b749bb5a50/XXXL"
        )
        saveBook(
            db,
            "Бессонница",
            "Минск, ул. Герцена, 1А",
            "65р",
            "Европейская, смешанная",
            "https://avatars.mds.yandex.net/get-altay/14021276/2a00000191992cf98d8f251f9ecba0640026/XXXL"
        )
        saveBook(
            db,
            "Le Gosse",
            "Минск, Революционная ул., 2",
            "43р",
            "Европейская, итальянская, фьюжн, французская, авторская, домашняя, средиземноморская",
            "https://avatars.mds.yandex.net/get-altay/13294935/2a000001932f1f5b3d02d513787086f5d8ae/XXXL"
        )*/

        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = RegistrationScreenObject){
                composable<RegistrationScreenObject> {
                    RegistrationScreen { navData ->
                        navController.navigate(navData)
                    }
                }

                composable<UserAuthData>{ navEntry ->
                    val userData = navEntry.toRoute<UserAuthData>()
                    MainScreen(userData)
                }
            }
        }
    }
}



private fun saveBook(
    firestore: FirebaseFirestore,
    name: String,
    address: String,
    avgBill: String,
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
            avgBill = avgBill,
            kitchenType = kitchenType,
            photoLinks = photoLinks
        ))
}
