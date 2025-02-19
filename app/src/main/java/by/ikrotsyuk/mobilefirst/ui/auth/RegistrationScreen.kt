package by.ikrotsyuk.mobilefirst.ui.auth

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun RegistrationScreen() {
    var auth = Firebase.auth

    var emailEditState = remember {
        mutableStateOf("")
    }

    var passwordEditState = remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Authorization", fontSize = 28.sp, modifier = Modifier.padding(bottom = 20.dp))

        TextField(emailEditState.value, onValueChange = {
            emailEditState.value = it
        }, placeholder = {
            if(emailEditState.value.isEmpty())
                Text(text = "Enter your email", color = Color.Gray, fontSize = 16.sp)
        })

        Spacer(modifier = Modifier.height(20.dp))

        TextField(passwordEditState.value, onValueChange = {
            passwordEditState.value = it
        }, placeholder = {
            if(passwordEditState.value.isEmpty())
                Text(text = "Enter your email", color = Color.Gray, fontSize = 16.sp)
        })

        Spacer(modifier = Modifier.height(20.dp))

        Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth(0.7f)){
            Button(colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue
            ), onClick = {
                firebaseSignUp(auth, emailEditState.value, passwordEditState.value)
            }) {
                Text(text = "Sign Up", fontSize = 16.sp)
            }

            Button(colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue
            ), onClick = {
                firebaseSignIn(auth, emailEditState.value, passwordEditState.value)
            }) {
                Text(text = "Sign In", fontSize = 16.sp)
            }
        }
    }
}

private fun firebaseSignUp(auth: FirebaseAuth, email: String, password: String){
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener {
            if(it.isSuccessful)
                Log.d("MyLog", "Sign Up successful!")
            else
                Log.d("MyLog", "Sign Up not successful!")

        }
}

private fun firebaseSignIn(auth: FirebaseAuth, email: String, password: String){
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener {
            if(it.isSuccessful)
                Log.d("MyLog", "Sign In successful!")
            else
                Log.d("MyLog", "Sign In not successful!")

        }
}

private fun firebaseSignOut(auth: FirebaseAuth){
    auth.signOut()
}

private fun firebaseDeleteAccount(auth: FirebaseAuth, email: String, password: String){
    var credential = EmailAuthProvider.getCredential(email, password)
    auth.currentUser?.reauthenticate(credential)
        ?.addOnCompleteListener{
            if(it.isSuccessful){
                auth.currentUser?.delete()
                    ?.addOnCompleteListener{
                        if(it.isSuccessful)
                            Log.d("MyLog", "Deleted successful!")
                        else
                            Log.d("MyLog", "Not Deleted (")
                    }
            }else
                Log.d("MyLog", "Failure reauthentication")
        }
}