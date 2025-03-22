package by.ikrotsyuk.mobilefirst.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import by.ikrotsyuk.mobilefirst.dto.FavouriteDTO
import by.ikrotsyuk.mobilefirst.dto.RestaurantDTO
import by.ikrotsyuk.mobilefirst.ui.components.RestaurantListItem
import by.ikrotsyuk.mobilefirst.ui.data.bottomNavigation.AppNavObject
import by.ikrotsyuk.mobilefirst.ui.nav_menu.NavigationMenu
import by.ikrotsyuk.mobilefirst.ui.nav_menu.NavigationMenuItem
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

@Composable
fun MainScreen(
    data: AppNavObject,
    onDetailedItemClick: (RestaurantDTO) -> Unit,
    onProfileNavClick: () -> Unit
) {

    val restaurantsListState = remember {
        mutableStateOf(emptyList<RestaurantDTO>())
    }

    val db = remember {
        Firebase.firestore
    }

    val selectedBottomMenuState = remember {
        mutableStateOf(data.title)
    }

    val isFavouriteListEmptyState = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        if(data.title.equals(NavigationMenuItem.RestaurantsList.title)) {
            selectedBottomMenuState.value = NavigationMenuItem.RestaurantsList.title
            getFavouriteRestaurantsKeys(db, data.uid) { favourites ->
                getRestaurants(db, favourites) { restaurants ->
                    restaurantsListState.value = restaurants
                }
            }
        } else if(data.title.equals(NavigationMenuItem.Favourites.title)){
            selectedBottomMenuState.value = NavigationMenuItem.Favourites.title
            getFavouriteRestaurantsKeys(db, data.uid){ favourites ->
                getFavouriteRestaurants(db, favourites){ restaurants ->
                    isFavouriteListEmptyState.value = restaurants.isEmpty()
                    restaurantsListState.value = restaurants
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationMenu(
                selectedTitle = selectedBottomMenuState.value,
                onRestaurantsClick = {
                    selectedBottomMenuState.value = NavigationMenuItem.RestaurantsList.title

                    getFavouriteRestaurantsKeys(db, data.uid){ favourites ->
                        getRestaurants(db, favourites){ restaurants ->
                            restaurantsListState.value = restaurants
                        }
                    }
                },
                onFavouritesClick = {
                    selectedBottomMenuState.value = NavigationMenuItem.Favourites.title

                    getFavouriteRestaurantsKeys(db, data.uid){ favourites ->
                        getFavouriteRestaurants(db, favourites){ restaurants ->
                            isFavouriteListEmptyState.value = restaurants.isEmpty()
                            restaurantsListState.value = restaurants
                        }
                    }
                },
                onProfileClick = {
                    selectedBottomMenuState.value = NavigationMenuItem.Profile.title
                    onProfileNavClick()
                }
            )
        }
    ) { paddingValue ->
        if(isFavouriteListEmptyState.value && (selectedBottomMenuState.value == NavigationMenuItem.Favourites.title)) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No favourites yet...",
                    fontSize = 16.sp,
                    color = Color.DarkGray
                )
            }
        }
        LazyColumn(
            modifier = Modifier.padding(paddingValue),
        ) {
            items(restaurantsListState.value){ restaurant ->
                RestaurantListItem(
                    restaurant,
                    onItemClick = {
                        onDetailedItemClick(restaurant)
                    },
                    onFavoutiteClick = {
                        restaurantsListState.value = restaurantsListState.value.map {
                            if(it.key == restaurant.key) {
                                editFavourites(
                                    db,
                                    !it.isFavourite,
                                    data.uid,
                                    FavouriteDTO(it.key)
                                )
                                it.copy(isFavourite = !it.isFavourite)
                            } else
                                it
                        }
                        if(selectedBottomMenuState.value == NavigationMenuItem.Favourites.title) {
                            restaurantsListState.value = restaurantsListState.value.filter { it.isFavourite }
                            isFavouriteListEmptyState.value = restaurantsListState.value.isEmpty()
                        }
                    }
                )
            }
        }
    }
}

private fun getRestaurants(
    db: FirebaseFirestore,
    favouriteKeys: List<String>,
    onGetRestaurants: (List<RestaurantDTO>) -> Unit
){
    db.collection("restaurants")
        .get()
        .addOnSuccessListener { task ->
            onGetRestaurants(task.toObjects(RestaurantDTO::class.java).map {
                if(favouriteKeys.contains(it.key))
                    it.copy(isFavourite = true)
                else
                    it
            })
        }
}

private fun getFavouriteRestaurants(
    db: FirebaseFirestore,
    favouriteKeys: List<String>,
    onGetRestaurants: (List<RestaurantDTO>) -> Unit
){
    if(favouriteKeys.isNotEmpty()) {
        db.collection("restaurants")
            .whereIn(FieldPath.documentId(), favouriteKeys)
            .get()
            .addOnSuccessListener { task ->
                Log.d("error img", "wadaw")
                onGetRestaurants(task.toObjects(RestaurantDTO::class.java).map {
                    if (favouriteKeys.contains(it.key))
                        it.copy(isFavourite = true)
                    else
                        it
                })
            }
    }else
        onGetRestaurants(emptyList())
}

private fun getFavouriteRestaurantsKeys(
    db: FirebaseFirestore,
    uid: String,
    onGetFavourites: (List<String>) -> Unit,
){
    db.collection("users")
        .document(uid)
        .collection("favourites")
        .get()
        .addOnSuccessListener { task ->
            val favourites = task.toObjects(FavouriteDTO::class.java)
            val favouriteKeys = arrayListOf<String>()
            favourites.forEach {
                favouriteKeys.add(it.key)
            }
            onGetFavourites(favouriteKeys)
        }.addOnFailureListener{
            onGetFavourites(emptyList())
        }
}

private fun editFavourites(
    db: FirebaseFirestore,
    isFavourite: Boolean,
    uid: String,
    favourite: FavouriteDTO
){
    if(isFavourite){
        db.collection("users")
            .document(uid)
            .collection("favourites")
            .document(favourite.key)
            .set(favourite)
    }else{
        db.collection("users")
            .document(uid)
            .collection("favourites")
            .document(favourite.key)
            .delete()
    }
}