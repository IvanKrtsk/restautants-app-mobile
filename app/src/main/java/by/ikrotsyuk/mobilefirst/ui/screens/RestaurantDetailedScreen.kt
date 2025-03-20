package by.ikrotsyuk.mobilefirst.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.ikrotsyuk.mobilefirst.ui.data.detailed.DetailedScreenObject
import coil3.compose.AsyncImage

@Composable
fun RestaurantDetailedScreen(
    detailedScreenObject: DetailedScreenObject
){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .fillMaxHeight(0.5f)
                .padding(5.dp)
                .clip(RoundedCornerShape(10.dp))
        ) {
            AsyncImage(
                model = detailedScreenObject.photoLinks,
                contentDescription = "img",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(Modifier.fillMaxWidth().fillMaxWidth(0.05f))
        Column(
            modifier = Modifier.padding(15.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = detailedScreenObject.name,
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp
            )
            Spacer(Modifier.fillMaxWidth().fillMaxHeight(0.05f))
            Text(
                text = detailedScreenObject.kitchenType,
                fontSize = 20.sp
            )
            Spacer(Modifier.fillMaxWidth().fillMaxHeight(0.05f))
            Text(
                text = "Адрес:" + detailedScreenObject.address,
                fontSize = 20.sp,
                color = Color.LightGray
            )
        }
    }
}