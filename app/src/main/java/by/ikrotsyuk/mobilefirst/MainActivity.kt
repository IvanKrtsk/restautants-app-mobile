package by.ikrotsyuk.mobilefirst

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import by.ikrotsyuk.mobilefirst.ui.auth.RegistrationScreen
import by.ikrotsyuk.mobilefirst.ui.screens.MainScreen
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
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
            RegistrationScreen()
            //MainScreen()
        }
    }
}
