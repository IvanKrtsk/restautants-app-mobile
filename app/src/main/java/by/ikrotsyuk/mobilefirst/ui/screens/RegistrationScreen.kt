package by.ikrotsyuk.mobilefirst.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import by.ikrotsyuk.mobilefirst.dto.UserInfoDTO
import by.ikrotsyuk.mobilefirst.ui.data.auth.UserAuthData
import by.ikrotsyuk.mobilefirst.ui.components.CustomTextField
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@Composable
fun RegistrationScreen(
    onNavigationToMainScreen: (UserAuthData) -> Unit
) {
    val auth = remember {
        Firebase.auth
    }

    val db = remember {
        Firebase.firestore
    }

    val emailEditState = remember {
        mutableStateOf("")
    }

    val passwordEditState = remember {
        mutableStateOf("")
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    if(auth.currentUser != null) {
        onNavigationToMainScreen(
            UserAuthData(
                uid = auth.currentUser?.uid!!,
                email = auth.currentUser?.email!!
            )
        )
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Restaurants",
                fontSize = 28.sp,
                modifier = Modifier.padding(bottom = 50.dp)
            )

            CustomTextField(
                text = emailEditState.value,
                hint = "Enter your email"
            ) {
                emailEditState.value = it
            }

            Spacer(modifier = Modifier.height(20.dp))

            CustomTextField(
                text = passwordEditState.value,
                hint = "Enter your password"
            ) {
                passwordEditState.value = it
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Button(colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                ), onClick = {
                    firebaseSignUp(auth,
                        db,
                        emailEditState.value,
                        passwordEditState.value,
                        onSignUpSuccess = { navData ->
                            onNavigationToMainScreen(navData)
                        },
                        onSignUpFailure = { error ->
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Error while signing up: $error!",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        })
                }) {
                    Text(text = "Sign Up", fontSize = 16.sp)
                }

                Button(colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                ), onClick = {
                    firebaseSignIn(auth,
                        db,
                        emailEditState.value,
                        passwordEditState.value,
                        onSignInSuccess = { navData ->
                            onNavigationToMainScreen(navData)
                        },
                        onSignInFailure = { error ->
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Error while signing up: $error!",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        })
                }) {
                    Text(text = "Sign In", fontSize = 16.sp)
                }
            }
        }

        Box(modifier = Modifier.padding(top = 25.dp)) {
            SnackbarHost(hostState = snackbarHostState)
        }
    }
}

private fun firebaseSignUp(auth: FirebaseAuth,
                           db: FirebaseFirestore,
                           email: String,
                           password: String,
                           onSignUpSuccess: (UserAuthData) -> Unit,
                           onSignUpFailure: (String) -> Unit)
{
    if(email.isBlank() || password.isBlank()) {
        onSignUpFailure("Email or password field is empty")
        return
    }
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener {
            if (it.isSuccessful)
                db.collection("users")
                    .document(auth.uid!!)
                    .set(UserInfoDTO(
                        name = "",
                        surname = "",
                        email = email,
                        age = "",
                        city = "",
                        address = "",
                        accountCreationDate = Timestamp.now().toDate().toString(),
                        lastSignedIn = Timestamp.now().toDate().toString()
                    ))
                onSignUpSuccess(
                    UserAuthData(
                        uid = it.result.user?.uid!!,
                        email = it.result.user?.email!!
                    )
                )
        }.addOnFailureListener {
            onSignUpFailure(it.message ?: "Sign Up error")
        }

}

private fun firebaseSignIn(auth: FirebaseAuth,
                           db: FirebaseFirestore,
                           email: String,
                           password: String,
                           onSignInSuccess: (UserAuthData) -> Unit,
                           onSignInFailure: (String) -> Unit)
{
    if(email.isBlank() || password.isBlank()){
        onSignInFailure("Email or password field is empty")
        return
    }
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener {
            if(it.isSuccessful) {
                db.collection("users")
                    .document(it.result.user?.uid!!)
                    .update("lastSignedIn", Timestamp.now().toDate().toString())
                onSignInSuccess(
                    UserAuthData(
                        uid = it.result.user?.uid!!,
                        email = it.result.user?.email!!
                    )
                )
            }
        }.addOnFailureListener{
            onSignInFailure(it.message ?: "Sign In error")
        }
}