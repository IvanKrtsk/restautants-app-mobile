package by.ikrotsyuk.mobilefirst

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import by.ikrotsyuk.mobilefirst.dto.RestaurantDTO
import by.ikrotsyuk.mobilefirst.ui.data.auth.RegistrationScreenObject
import by.ikrotsyuk.mobilefirst.ui.data.bottomNavigation.AppNavObject
import by.ikrotsyuk.mobilefirst.ui.data.detailed.DetailedScreenObject
import by.ikrotsyuk.mobilefirst.ui.data.bottomNavigation.ProfileNavScreenObject
import by.ikrotsyuk.mobilefirst.ui.nav_menu.NavigationMenuItem
import by.ikrotsyuk.mobilefirst.ui.screens.MainScreen
import by.ikrotsyuk.mobilefirst.ui.screens.ProfileScreen
import by.ikrotsyuk.mobilefirst.ui.screens.RegistrationScreen
import by.ikrotsyuk.mobilefirst.ui.screens.RestaurantDetailedScreen
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Firebase.firestore

        /*var list: List<RestaurantDTO> = emptyList()
        getRestaurants(db){ restaurants ->
            list = restaurants
        }

        var set: HashSet<String> = HashSet()
        list.forEach {
            if(set.contains(it.key)) {
                db.collection("restaurants")
                    .document(it.key)
                    .delete()
            } else
                set.add(it.key)
        }*/
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = RegistrationScreenObject){
                composable<RegistrationScreenObject> {
                    RegistrationScreen { navData ->
                        navController.navigate(AppNavObject(
                            NavigationMenuItem.RestaurantsList.title,
                            navData.uid,
                            navData.email
                        ))
                    }
                }

                composable<AppNavObject>{ navEntry ->
                    val navObject = navEntry.toRoute<AppNavObject>()
                    MainScreen(
                        navObject,
                        onDetailedItemClick = { restaurant ->
                            navController.navigate(DetailedScreenObject(
                                name = restaurant.name,
                                address = restaurant.address,
                                avgBill = restaurant.avgBill,
                                kitchenType = restaurant.kitchenType,
                                photoLinks = restaurant.photoLinks,
                                isFavourite = restaurant.isFavourite
                            ))
                        },
                        onProfileNavClick = {
                            navController.navigate(ProfileNavScreenObject)
                        }
                    )
                }

                composable<DetailedScreenObject>{ navEntry ->
                    val navData = navEntry.toRoute<DetailedScreenObject>()
                    RestaurantDetailedScreen(navData)
                }

                composable<ProfileNavScreenObject>{ navEntry ->
                    val profileData = navEntry.toRoute<ProfileNavScreenObject>()
                    ProfileScreen(
                        profileData,
                        onAppContentClick = { bottomMenuItem ->
                            navController.navigate(bottomMenuItem)
                        },
                        onSystemOut = {
                            navController.navigate(RegistrationScreenObject)
                        }
                    )
                }
            }
        }
    }
}

private fun getRestaurants(
    db: FirebaseFirestore,
    onGetRestaurants: (List<RestaurantDTO>) -> Unit
){
    db.collection("restaurants")
        .get()
        .addOnSuccessListener { task ->
            onGetRestaurants(task.toObjects(RestaurantDTO::class.java))
        }
}

private fun saveRestaurant(
    firestore: FirebaseFirestore,
    name: String,
    address: String,
    avgBill: String,
    kitchenType: String,
    photoLinks: MutableList<String>,
){
    val db = firestore.collection("restaurants")
    val key = db.document().id
    Log.d("try save", "awdawda")
    db.document(key)
        .set(RestaurantDTO(
            key = key,
            name = name,
            address = address,
            avgBill = avgBill,
            kitchenType = kitchenType,
            photoLinks = photoLinks
        ))
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("save done", "Сохранение успешно завершено")
            } else {
                Log.d("save error", "Ошибка завершения: ${task.exception?.message}")
            }
        }
        .addOnFailureListener { exception ->
            Log.d("save error", "Ошибка сохранения: ${exception.message}")
        }
}


/*saveRestaurant(
                db,
                "Madmen",
                "Минск, Интернациональная ул., 25А",
                "35р",
                "Европейская",
                "https://avatars.mds.yandex.net/get-altay/10879408/2a00000190b772379cca0be2a045437d32b6/XXXL"
            )
            saveRestaurant(
                db,
                "Honky Tonk",
                "Минск, площадь Свободы, 23",
                "50р",
                "Европейская, американская",
                "https://avatars.mds.yandex.net/get-altay/14813057/2a000001942cc3f30c61204cdf23dd1ddfda/XXXL"
            )
        saveRestaurant(
            db,
            "Astoria Central",
            "Интернациональная ул., 25А",
            "60р",
            "Европейская, белорусская, японская",
            "https://avatars.mds.yandex.net/get-altay/13325287/2a0000018ef98d1051eb55e354a055a75169/XXXL"
        )
        saveRestaurant(
            db,
            "Гамбринус",
            "Минск, площадь Свободы, 2",
            "45р",
            "Европейская, белорусская, ирландская, немецкая, русская, чешская, английская, славянских народов",
            "https://avatars.mds.yandex.net/get-altay/492546/2a0000015f6c85536e1246d757517aa64cb9/XXXL"
        )
        saveRestaurant(
            db,
            "Marbl",
            "Минск, площадь Свободы, 4",
            "100р",
            "Европейская, итальянская, испанская, греческая, французская, авторская, морская, мясная, рыбная, средиземноморская, вегетарианская, постная",
            "https://avatars.mds.yandex.net/get-altay/14165812/2a00000191c8ab26024ecaf398a304e70f58/XXXL"
        )
        saveRestaurant(
            db,
            "Старый дом",
            "Минск, ул. Кирилла и Мефодия, 6А",
            "40р",
            "Европейская, белорусская, домашняя",
            "https://avatars.mds.yandex.net/get-altay/5098065/2a0000018192246e4c1f318f53b749bb5a50/XXXL"
        )
        saveRestaurant(
            db,
            "Бессонница",
            "Минск, ул. Герцена, 1А",
            "65р",
            "Европейская, смешанная",
            "https://avatars.mds.yandex.net/get-altay/14021276/2a00000191992cf98d8f251f9ecba0640026/XXXL"
        )
        saveRestaurant(
            db,
            "Le Gosse",
            "Минск, Революционная ул., 2",
            "43р",
            "Европейская, итальянская, фьюжн, французская, авторская, домашняя, средиземноморская",
            "https://avatars.mds.yandex.net/get-altay/13294935/2a000001932f1f5b3d02d513787086f5d8ae/XXXL"
        )*/
/*saveRestaurant(
    db,
    "Скиф",
    "Минск, Фрунзе",
    "35р",
    "Белорусская",
    mutableListOf("https://avatars.mds.yandex.net/get-altay/10879408/2a00000190b772379cca0be2a045437d32b6/XXXL", "https://hozyain.by/wp-content/uploads/2016/07/353307_pchela_cvetok_fon_1680x1050_www.GetBg_.net_.jpg")
)*/
/*saveRestaurant(
    db,
    "Пена Дней",
    "Минск, Интернациональная ул., 23",
    "65р",
    "Европейская, белорусская, итальянская, русская, французская, авторская, домашняя, смешанная, национальная",
    mutableListOf("https://avatars.mds.yandex.net/get-altay/10702222/2a000001899d6ca7fbff378cedb50fead4a2/M_height", "https://avatars.mds.yandex.net/get-altay/11937297/2a0000018e0a42d0ac053c5cf16946f1570d/XXXL", "https://avatars.mds.yandex.net/get-altay/9428388/2a000001899d6b436428bb0b1f3c96a07117/XXXL", "https://avatars.mds.yandex.net/get-altay/5098065/2a000001818020d6a02c481c7b196ac3be66/XXXL", "https://avatars.mds.yandex.net/get-altay/9916116/2a000001899d6abae7f53249acf751cc937b/XXXL", "https://avatars.mds.yandex.net/get-altay/7979597/2a00000189e93ea382ffa701a62f43ec7435/XXXL", "https://avatars.mds.yandex.net/get-altay/14007310/2a000001958636141cfc4cd06c23e78e86eb/XXXL")
)
saveRestaurant(
    db,
    "Salt",
    "Минск, Интернациональная ул., 17",
    "70р",
    "Европейская, рыбная, средиземноморская",
    mutableListOf("https://avatars.mds.yandex.net/get-altay/11004775/2a0000018cf9fe2a3e0a1bb767f630738a5f/XXXL", "https://avatars.mds.yandex.net/get-altay/9761215/2a0000018cf9fde9b36762085f1685fcd7e0/XXXL", "https://avatars.mds.yandex.net/get-altay/11401274/2a0000018cf9fde8be9d76f0e1fda814bed4/XXXL", "https://avatars.mds.yandex.net/get-altay/11371852/2a0000018cf9fde9d19491fd3026d3f86cea/XXXL", "https://avatars.mds.yandex.net/get-altay/6363798/2a0000018cf9fdeb45e75e674ecefbf23843/XXXL", "https://avatars.mds.yandex.net/get-altay/14264437/2a000001940394003d7317872a5aa081c70b/XXXL", "https://avatars.mds.yandex.net/get-altay/1881447/2a0000018cf9fde9c28752dffa84d9a2fbd6/XXXL", "https://avatars.mds.yandex.net/get-altay/11187599/2a0000018cfa0bc772b574203cdb46331bf4/XXXL")
)
saveRestaurant(
    db,
    "Личи",
    "Минск, просп. Победителей, 1",
    "50р",
    "Китайская",
    mutableListOf("https://avatars.mds.yandex.net/get-altay/10700956/2a0000018c30047bcdae1eeb4baf3342a87b/XXXL", "https://avatars.mds.yandex.net/get-altay/6119709/2a00000180899a03021b2475dfda2fe7f818/XXXL", "https://avatars.mds.yandex.net/get-altay/6319069/2a00000184e6f69d1c52092b242ca43d8ebf/XXXL", "https://avatars.mds.yandex.net/get-altay/14171651/2a00000194327feff498f0ac7bafd5257960/XXXL", "https://avatars.mds.yandex.net/get-altay/6065996/2a0000018089997fac90f311fbc79ed6bf46/XXXL", "https://avatars.mds.yandex.net/get-altay/14402904/2a00000193e5a762e0f24615e1faa3941685/XXXL", "https://avatars.mds.yandex.net/get-altay/6057477/2a0000018089996656aca74b43d9ec684e66/XXXL", "https://avatars.mds.yandex.net/get-altay/11563767/2a0000019161316ff71e2b36c4a596c5fff2/XXXL")
)
saveRestaurant(
    db,
    "L'angolo Italiano",
    "Минск, Раковская ул., 36",
    "75р",
    "Европейская, итальянская, авторская",
    mutableListOf("https://avatars.mds.yandex.net/get-altay/914620/2a0000018fd45abb14b7373e510915cc32ca/XXXL", "https://avatars.mds.yandex.net/get-altay/4598810/2a0000017c1d8b3b40132e7fc825670980ec/XXXL", "https://avatars.mds.yandex.net/get-altay/13220786/2a00000191902c130f231af441c7ac8d1ed0/XXXL", "https://avatars.mds.yandex.net/get-altay/5751673/2a0000017d0a068d3e35a7508dc615a7570f/XXXL", "https://avatars.mds.yandex.net/get-altay/11406796/2a0000018ceecbb2d47eee87779d3d118e20/XXXL", "https://avatars.mds.yandex.net/get-altay/11374564/2a000001931601507b44e2aeaeed1732657d/XXXL", "https://avatars.mds.yandex.net/get-altay/7186075/2a000001867002f8d2630e1d543d7771efca/XXXL", "https://avatars.mds.yandex.net/get-altay/918124/2a00000186d01e99d4dcf2111b42724d278d/XXXL", "https://avatars.mds.yandex.net/get-altay/4562252/2a000001825f9bbfb353454d367d1e728ef2/XXXL")
)
saveRestaurant(
    db,
    "Beer House",
    "Минск, Раковская ул., 23",
    "60р",
    "Европейская, авторская",
    mutableListOf("https://avatars.mds.yandex.net/get-altay/11886651/2a0000018e296f1649041c44eeef102b3eda/XXXL", "https://avatars.mds.yandex.net/get-altay/14381528/2a0000019562d6077448c9638ed036378a53/XXXL", "https://avatars.mds.yandex.net/get-altay/13754922/2a00000193467dcd667677e230bbcfcf43a8/XXXL", "https://avatars.mds.yandex.net/get-altay/11376278/2a00000191714d2ed740d9a3039a30b5d9cd/XXXL", "https://avatars.mds.yandex.net/get-altay/11018317/2a0000018c43fe4acced103c866779079471/XXXL", "https://avatars.mds.yandex.net/get-altay/10829645/2a0000018b99c54af4cb5a43771b0669fe47/XXXL", "https://avatars.mds.yandex.net/get-altay/11383855/2a0000018ba5a1169492002f2e253ecd1cab/XXXL")
)
saveRestaurant(
    db,
    "Пекарни Пирогова",
    "Минск, Раковская ул., 25, корп. 1",
    "30р",
    "Пироговая",
    mutableListOf("https://avatars.mds.yandex.net/get-altay/13987456/2a000001917a3d4c366cbcb908ff209e459e/XXXL", "https://avatars.mds.yandex.net/get-altay/7456447/2a0000018580c9dc6af4eca0c2d8f07a87d9/XXXL", "https://avatars.mds.yandex.net/get-altay/10247515/2a0000019093b1bc09a7c5537182e100d081/XXXL", "https://avatars.mds.yandex.net/get-altay/13797017/2a0000019179d05ca826fb18c23e9d86cae3/XXXL", "https://avatars.mds.yandex.net/get-altay/13754922/2a00000191f2fac6730337ac946da8a2bd7d/XXXL", "https://avatars.mds.yandex.net/get-altay/13930885/2a00000191ff63c84fbec184ed11022d4733/XXXL", "https://avatars.mds.yandex.net/get-altay/7691421/2a00000184dc433f7c6e3d2c1f32ce47deab/XXXL")
)
saveRestaurant(
    db,
    "Куриный клуб",
    "Минск, Раковская ул., 23Б",
    "60р",
    "Европейская, смешанная",
    mutableListOf("https://avatars.mds.yandex.net/get-altay/14021521/2a000001936c317d85e35125091338563db6/XXXL", "https://avatars.mds.yandex.net/get-altay/11873493/2a000001936c389e60a690fef3cd3872a189/XXXL", "https://avatars.mds.yandex.net/get-altay/13525210/2a0000019521c8e1cf61a835c646a71a3b94/XXXL", "https://avatars.mds.yandex.net/get-altay/14215987/2a0000019437825adb822a5971a8b57536af/XXXL", "https://avatars.mds.yandex.net/get-altay/14451083/2a000001940e27706fd2d3cd7b06930c8b5e/XXXL", "https://avatars.mds.yandex.net/get-altay/15018138/2a000001944ec885d1f504b03977bf36047b/XXXL", "https://avatars.mds.yandex.net/get-altay/14021655/2a00000193e08e622364e67f9d712e66e635/XXXL")
)
saveRestaurant(
    db,
    "Синяя птица",
    "Минск, Юрово-Завальная ул., 15",
    "30р",
    "Пекарня",
        mutableListOf("https://avatars.mds.yandex.net/get-altay/15413153/2a00000195145369983ed80088fda73af91a/XXXL", "https://avatars.mds.yandex.net/get-altay/14402637/2a000001949f711a2bcea68fc0b09ff7e29f/XXXL", "https://avatars.mds.yandex.net/get-altay/15249330/2a00000194b14456e1eff6ed9025db5f2c5a/XXXL", "https://avatars.mds.yandex.net/get-altay/14381528/2a0000019494b9699f225c3b8e1a07b2bf07/XXXL", "https://avatars.mds.yandex.net/get-altay/15037708/2a0000019580682d13e37749c6580fdb6516/XXXL", "https://avatars.mds.yandex.net/get-altay/15352901/2a00000195408dec1bcafa5a5a6f21b9b003/XXXL", "https://avatars.mds.yandex.net/get-altay/14031726/2a00000194d72d9b69a8d607649cda9a7b5f/XXXL")
)*/