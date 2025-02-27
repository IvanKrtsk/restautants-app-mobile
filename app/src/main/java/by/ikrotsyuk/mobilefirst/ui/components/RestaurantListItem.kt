package by.ikrotsyuk.mobilefirst.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.ikrotsyuk.mobilefirst.dto.RestaurantDTO
import by.ikrotsyuk.mobilefirst.ui.theme.LikeButtonBg
import by.ikrotsyuk.mobilefirst.ui.theme.TransparentRed
import coil3.compose.AsyncImage

@Composable
fun RestaurantListItem(
    restaurant: RestaurantDTO,
    onFavoutiteClick: () -> Unit = {}
) {
    val height: Int = LocalConfiguration.current.screenHeightDp
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height((height * 0.22).dp)
            .padding(5.dp),
        shape = RoundedCornerShape(10.dp),
    ) {
        Row(
            modifier = Modifier.background(color = TransparentRed)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(0.4f)
                    .fillMaxHeight()
                    .padding(5.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                AsyncImage(
                    model = restaurant.photoLinks,
                    contentDescription = "img",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Button(
                    modifier = Modifier.align(Alignment.BottomEnd).padding(10.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LikeButtonBg
                    ),
                    onClick = {
                        onFavoutiteClick()
                    }
                ) {
                    Icon(
                        if(restaurant.isFavourite) {
                            Icons.Default.Favorite
                        }
                        else
                            Icons.Default.FavoriteBorder,
                        contentDescription = "favourite",
                        tint = if (restaurant.isFavourite) Color.Yellow else Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Spacer(Modifier.fillMaxHeight().fillMaxWidth(0.1f))
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(top = 15.dp)
            ) {
                Text(
                    text = restaurant.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height((height * 0.015).dp))
                Text(
                    text = "Кухня: ${restaurant.kitchenType}",
                    fontSize = 16.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height((height * 0.01).dp))
                Text(
                    text = "Средний чек: ${restaurant.avgBill}",
                    fontSize = 16.sp,
                )
                Spacer(modifier = Modifier.height((height * 0.01).dp))
                Text(
                    text = "Адрес: ${restaurant.address}",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        }
    }
}