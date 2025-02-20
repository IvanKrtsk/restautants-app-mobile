package by.ikrotsyuk.mobilefirst

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import by.ikrotsyuk.mobilefirst.ui.auth.data.RegistrationScreenObject
import by.ikrotsyuk.mobilefirst.ui.auth.data.UserAuthData
import by.ikrotsyuk.mobilefirst.ui.screens.MainScreen
import by.ikrotsyuk.mobilefirst.ui.screens.RegistrationScreen
import coil.annotation.ExperimentalCoilApi
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalCoilApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var fs = Firebase.firestore
        setContent {
            /*Box(modifier = Modifier.fillMaxSize()) {
                val painter = rememberImagePainter(data = "https://img.iamcook.ru/2023/upl/recipes/cat/u-6d14971651d67c24fc077e00786816e8.JPG")
                Image(painter = painter, contentDescription = null, modifier = Modifier.fillMaxSize())
            }*/
//            RegistrationScreen()
            //MainScreen()
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
