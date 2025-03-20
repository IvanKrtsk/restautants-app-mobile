package by.ikrotsyuk.mobilefirst.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.ikrotsyuk.mobilefirst.ui.data.bottomNavigation.AppNavObject
import by.ikrotsyuk.mobilefirst.ui.data.bottomNavigation.ProfileNavScreenObject
import by.ikrotsyuk.mobilefirst.ui.nav_menu.NavigationMenu
import by.ikrotsyuk.mobilefirst.ui.nav_menu.NavigationMenuItem
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    profile: ProfileNavScreenObject,
    onAppContentClick: (AppNavObject) -> Unit,
    onSystemOut: () -> Unit
){
    val auth = remember {
        com.google.firebase.ktx.Firebase.auth
    }

    val db = remember {
        Firebase.firestore
    }

    val selectedBottomMenuState = remember {
        mutableStateOf(NavigationMenuItem.RestaurantsList.title)
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationMenu(
                onRestaurantsClick = {
                    onAppContentClick(AppNavObject(
                        NavigationMenuItem.RestaurantsList.title,
                        uid = auth.currentUser?.uid!!,
                        email = auth.currentUser?.email!!
                    ))
                },
                onFavouritesClick = {
                    onAppContentClick(AppNavObject(
                        NavigationMenuItem.Favourites.title,
                        uid = auth.currentUser?.uid!!,
                        email = auth.currentUser?.email!!
                    ))
                },
                onProfileClick = {
                    selectedBottomMenuState.value = NavigationMenuItem.Profile.title
                }
            )
        }
    ) { paddingValue ->
        Column(
            modifier = Modifier.
            fillMaxSize().
            padding(paddingValue),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red),
                onClick = {
                    firebaseSignOut(
                        auth,
                        onLogOut = {
                            onSystemOut()
                        }
                    )
                }
            ) {
                Text(text = "Sign Out", fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red),
                onClick = {
                    firebaseDeleteAccount(
                        db,
                        onAccountDeleteSuccessful = {
                            onSystemOut()
                        },
                        onAccountDeleteFailed = {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Error while signing up: $it!",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                    )
                }
            ) {
                Text(text = "Delete Account", fontSize = 16.sp)
            }
        }

        Box(modifier = Modifier.padding(top = 25.dp)) {
            SnackbarHost(hostState = snackbarHostState)
        }
    }
}

private fun firebaseSignOut(
    auth: FirebaseAuth,
    onLogOut: () -> Unit
){
    auth.signOut()
    onLogOut()
}

private fun firebaseDeleteAccount(
    db: FirebaseFirestore,
    onAccountDeleteSuccessful: () -> Unit,
    onAccountDeleteFailed: (String) -> Unit
){
    val user = FirebaseAuth.getInstance().currentUser

    user?.delete()?.addOnCompleteListener {
        db.collection("users").document(user.uid).delete()
        onAccountDeleteSuccessful()
    }?.addOnFailureListener { task ->
        onAccountDeleteFailed(task.message ?: "Error while deleting")
    }
}