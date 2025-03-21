package by.ikrotsyuk.mobilefirst.dto

import com.google.firebase.Timestamp
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Locale

@Serializable
data class UserInfoDTO(
    var name: String = "",
    var surname: String = "",
    var email: String = "",
    var age: String = "",
    var city: String = "",
    var address: String = "",
    var accountCreationDate: String = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).format(Timestamp.now().toDate()),
    var lastSignedIn: String = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).format(Timestamp.now().toDate())
)
