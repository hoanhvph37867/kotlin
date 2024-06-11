package fpoly.hoanhvph37867.asm_kotlin_ph37867

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import fpoly.hoanhvph37867.asm_kotlin_ph37867.models.Favorite
import fpoly.hoanhvph37867.asm_kotlin_ph37867.viewmodels.Favorite_Viewmodel


@Composable
fun FavoriteScreen(innerPadding: PaddingValues, viewmodel: Favorite_Viewmodel) {
    Column(modifier = Modifier) {
        FavoriteGrid(viewmodel)
    }
}

@Composable
fun FavoriteGrid(viewmodel: Favorite_Viewmodel) {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        ListFavorite(viewmodel = viewmodel)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp, bottom = 15.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Box(
                modifier = Modifier
                    .padding(7.dp)
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF242424))
                    .clickable(onClick = {
                        for (item in viewmodel._favorite.value) {
                            viewmodel.add_to_cart(
                                Favorite(
                                prId = item.prId,
                                prName = item.prName,
                                prPrice = item.prPrice,
                                prImg = item.prImg,
                                id = item.id,
                                rate = item.rate,
                                quantity = item.quantity,
                                description = item.description
                            )
                            )
                            viewmodel.deleteItemFav(item)
                        }
                        Toast.makeText(context, "Add all to cart", Toast.LENGTH_SHORT).show()
                    })
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Add all to my cart",
                        style = TextStyle(
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.nunitosans_7pt_condensed_light)),
                            fontWeight = FontWeight(600),
                            fontSize = 17.sp)
                    )
                }
            }
        }
    }
}

@Composable
fun ListFavorite(viewmodel: Favorite_Viewmodel) {
    val favoriteList = viewmodel._favorite.value
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(favoriteList.size) { item ->
            FavoriteItem(item = favoriteList[item], viewmodel)
        }
    }
    viewmodel.getFavorite()
}

@Composable
fun FavoriteItem(item: Favorite, viewmodel: Favorite_Viewmodel) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .background(Color.White),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = item.prImg, contentDescription = null, modifier = Modifier
                .width(110.dp)
                .height(120.dp), contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .width(200.dp)
                .padding(start = 10.dp)
                .fillMaxHeight()
        ) {
            Text(
                text = item.prName,
                fontSize = 15.sp,
                fontWeight = FontWeight(600),
                color = colorResource(
                    id = R.color.gray
                ),
                fontFamily = FontFamily(
                    Font(R.font.nunitosans_7pt_condensed_light)
                )
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = "\$ " + item.prPrice,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(
                    Font(R.font.nunitosans_7pt_condensed_bold)
                )
            )
        }
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(onClick = {
                viewmodel.deleteItemFav(item)
                Toast.makeText(context, "Delete ${item.prName}", Toast.LENGTH_SHORT).show()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.delete),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }

            Row {
                IconButton(onClick = {
                    viewmodel.add_to_cart(
                        Favorite(
                            prId = item.prId,
                            prName = item.prName,
                            prPrice = item.prPrice,
                            prImg = item.prImg,
                            id = item.id,
                            rate = item.rate,
                            quantity = item.quantity,
                            description = item.description
                        )
                    )
                    Toast.makeText(context, "Add ${item.prName} to cart", Toast.LENGTH_SHORT).show()
                    viewmodel.deleteItemFav(item)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.bag),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

