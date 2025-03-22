package by.ikrotsyuk.mobilefirst.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
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
import by.ikrotsyuk.mobilefirst.ui.theme.Orange
import coil3.compose.AsyncImage

@Composable
fun RestaurantDetailedScreen(
    detailedScreenObject: DetailedScreenObject
){
    val pagerState = rememberPagerState(initialPage = 0) {
        detailedScreenObject.photoLinks.size
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .fillMaxHeight(0.5f)
                .padding(5.dp)
                .clip(RoundedCornerShape(10.dp))
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                AsyncImage(
                    model = detailedScreenObject.photoLinks[page],
                    contentDescription = "Image $page",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Spacer(Modifier.fillMaxWidth().fillMaxWidth(0.05f))
        Column(
            modifier = Modifier.padding(15.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = detailedScreenObject.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp
                )
                Spacer(Modifier.fillMaxWidth(0.9f))
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        if (detailedScreenObject.isFavourite) {
                            Icons.Default.Favorite
                        } else
                            Icons.Default.FavoriteBorder,
                        contentDescription = "favourite",
                        tint = if (detailedScreenObject.isFavourite) Orange else Color.Gray,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            Spacer(Modifier.fillMaxWidth().fillMaxHeight(0.05f))
            Text(
                text = "Кухня: " + detailedScreenObject.kitchenType,
                fontSize = 20.sp
            )
            Spacer(Modifier.fillMaxWidth().fillMaxHeight(0.05f))
            Text(
                text = "Средний чек: " + detailedScreenObject.avgBill,
                fontSize = 20.sp
            )
            Spacer(Modifier.fillMaxWidth().fillMaxHeight(0.05f))
            Text(
                text = "Адрес: " + detailedScreenObject.address,
                fontSize = 20.sp,
                color = Color.Gray
            )
        }
    }
}