package by.ikrotsyuk.mobilefirst.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import by.ikrotsyuk.mobilefirst.ui.theme.LightBlue

@Composable
fun CustomTextField(
    text: String,
    hint: String,
    onValueChange: (String) -> Unit
) {
    TextField(text, onValueChange = {
        onValueChange(it)
    }, placeholder = {
        if(text.isEmpty())
            Text(text = hint, color = Color.Gray, fontSize = 16.sp)
    }, colors = TextFieldDefaults.colors(
        unfocusedContainerColor = LightBlue,
        focusedContainerColor = LightBlue
    ),
        singleLine = true,
        modifier = Modifier.fillMaxWidth(0.7f)
    )
}