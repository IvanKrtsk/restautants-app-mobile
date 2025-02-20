package by.ikrotsyuk.mobilefirst.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.ikrotsyuk.mobilefirst.dto.RestaurantDTO
import by.ikrotsyuk.mobilefirst.ui.theme.TransparentRed
import coil3.compose.AsyncImage

@Composable
fun RestaurantListItem(
    restaurant: RestaurantDTO,
) {
    val height: Int = LocalConfiguration.current.screenHeightDp
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height((height * 0.2).dp)
            .padding(5.dp),
        shape = RoundedCornerShape(10.dp),
    ) {
        Row(
            modifier = Modifier.background(color = TransparentRed)
        ) {
            AsyncImage(
                model = restaurant.photoLinks,
                contentDescription = "img",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth(0.4f).fillMaxHeight()
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth().padding(top = 5.dp)
            ) {
                Text(
                    text = restaurant.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height((height * 0.02).dp))
                Text(
                    text = restaurant.kitchenType
                )
                Spacer(modifier = Modifier.height((height * 0.02).dp))
                Text(
                    text = restaurant.address
                )
            }
        }
    }
}