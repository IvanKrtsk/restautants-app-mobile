package by.ikrotsyuk.mobilefirst.ui.screens

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.ikrotsyuk.mobilefirst.dto.UserInfoDTO
import by.ikrotsyuk.mobilefirst.ui.components.CustomTextField
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

    val userDataNameEditState = remember {
        mutableStateOf("")
    }

    val userDataSurnameEditState = remember {
        mutableStateOf("")
    }

    val userDataAgeEditState = remember {
        mutableStateOf("")
    }

    val userDataCityEditState = remember {
        mutableStateOf("")
    }

    val userDataAddressEditState = remember {
        mutableStateOf("")
    }

    val userDataEmailEditState = remember {
        mutableStateOf("")
    }
    val userDataAccountCreationDateEditState = remember {
        mutableStateOf("")
    }

    val userDataLastSignedInEditState = remember {
        mutableStateOf("")
    }

    val selectedBottomMenuState = remember {
        mutableStateOf(NavigationMenuItem.Profile.title)
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        getUserData(
            db,
            auth.uid!!,
            onGetUserInfo = { info ->
                userDataNameEditState.value = info.name
                userDataSurnameEditState.value = info.surname
                userDataAgeEditState.value = info.age
                userDataCityEditState.value = info.city
                userDataAddressEditState.value = info.address
                userDataEmailEditState.value = info.email
                userDataAccountCreationDateEditState.value = info.accountCreationDate
                userDataLastSignedInEditState.value = info.lastSignedIn
            }
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationMenu(
                selectedTitle = selectedBottomMenuState.value,
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            CustomTextField(
                text = userDataNameEditState.value,
                hint = "Enter your name"
            ) {
                userDataNameEditState.value = it
            }

            Spacer(modifier = Modifier.height(20.dp))

            CustomTextField(
                text = userDataSurnameEditState.value,
                hint = "Enter your surname"
            ) {
                userDataSurnameEditState.value = it
            }

            Spacer(modifier = Modifier.height(20.dp))

            CustomTextField(
                text = userDataAgeEditState.value,
                hint = "Enter your age",
            ) {
                userDataAgeEditState.value = it
            }

            Spacer(modifier = Modifier.height(20.dp))

            CustomTextField(
                text = userDataCityEditState.value,
                hint = "Enter your city",
            ) {
                userDataCityEditState.value = it
            }

            Spacer(modifier = Modifier.height(20.dp))

            CustomTextField(
                text = userDataAddressEditState.value,
                hint = "Enter your address",
            ) {
                userDataAddressEditState.value = it
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Email: ${userDataEmailEditState.value}")
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Registered at: ${userDataAccountCreationDateEditState.value}")
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Entered at: ${userDataLastSignedInEditState.value}")

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red),
                onClick = {
                    updateUserData(
                        UserInfoDTO(
                            name = userDataNameEditState.value,
                            surname = userDataSurnameEditState.value,
                            age = userDataAgeEditState.value,
                            email = userDataEmailEditState.value,
                            city = userDataCityEditState.value,
                            address = userDataAddressEditState.value,
                            accountCreationDate = userDataAccountCreationDateEditState.value,
                            lastSignedIn = userDataLastSignedInEditState.value
                        ),
                        db,
                        auth.uid!!
                    )
                }
            ) {
                Text(text = "Update your information", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(40.dp))

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

private fun updateUserData(
    userInfoDTO: UserInfoDTO,
    db: FirebaseFirestore,
    uid: String
){
    db.collection("users")
        .document(uid)
        .set(userInfoDTO)
        .addOnFailureListener{
            Log.d("save error", it.message!!)
        }

}

private fun getUserData(
    db: FirebaseFirestore,
    uid: String,
    onGetUserInfo: (UserInfoDTO) -> Unit
){
    db.collection("users")
        .document(uid)
        .get()
        .addOnSuccessListener { task ->
            val info = task.toObject(UserInfoDTO::class.java)
            onGetUserInfo(info ?: UserInfoDTO())
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