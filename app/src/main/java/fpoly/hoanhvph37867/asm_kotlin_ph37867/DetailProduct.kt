package fpoly.hoanhvph37867.asm_kotlin_ph37867

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import coil.compose.AsyncImage
import fpoly.hoanhvph37867.asm_kotlin_ph37867.models.Product
import fpoly.hoanhvph37867.asm_kotlin_ph37867.viewmodels.Product_Viewmodel

@Composable
fun DetailsProduct(
    navController: NavController, name: String, price: String, image: String, rate: String, quantity: Int, description: String, id: String,
    viewmodel: Product_Viewmodel
) {
    val context = LocalContext.current.applicationContext
    val selectedQuantity = remember { mutableStateOf(1) } // Lưu số lượng sản phẩm đã chọn
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Custom(navController, image)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 20.dp, start = 20.dp, top = 10.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = name,
                fontSize = 24.sp,
                fontWeight = FontWeight(500),
                fontFamily = FontFamily(Font(R.font.gelasio_bold))
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "\$ ${price}",
                    fontSize = 30.sp,
                    fontWeight = FontWeight(700),
                    fontFamily = FontFamily(Font(R.font.nunitosans_7pt_condensed_bold))
                )
                Row(
                    modifier = Modifier.width(113.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    var quantity by remember { mutableStateOf(1) }
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(color = Color("#E0E0E0".toColorInt()))
                            .clickable {
                                selectedQuantity.value += 1
                                Log.e("quantity", selectedQuantity.value.toString())
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.add),
                            contentDescription = null,
                            modifier = Modifier
                                .clickable {
                                    selectedQuantity.value++
                                }
                                .size(10.dp)

                        )
                    }
                    Text(
                        text = "${selectedQuantity.value}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight(700),
                        fontFamily = FontFamily(
                            Font(R.font.nunitosans_7pt_condensed_bold)
                        )
                    )
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(color = Color("#E0E0E0".toColorInt())),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.apart),
                            contentDescription = null,
                            modifier = Modifier
                                .clickable {
                                    if (selectedQuantity.value > 1) selectedQuantity.value--
                                }
                                .size(20.dp)
                                .padding(5.dp)
                        )
                    }
                }
            }
            Row(modifier = Modifier.width(200.dp), verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.star),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = rate,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(700),
                    fontFamily = FontFamily(
                        Font(R.font.nunitosans_7pt_condensed_bold)
                    ),
                    modifier = Modifier.padding(7.dp)
                )
                Text(
                    text = "(50 reviews)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight(500),
                    color = Color("#808080".toColorInt()),
                    fontFamily = FontFamily(
                        Font(R.font.nunitosans_7pt_condensed_bold)
                    ),
                    modifier = Modifier.padding(start = 15.dp).clickable {
                        navController.navigate("rating")
                    }
                )
            }
            Text(
                text = description,
                fontSize = 15.sp,
                textAlign = TextAlign.Justify,
                fontWeight = FontWeight(500),
                color = Color("#606060".toColorInt()),
                fontFamily = FontFamily(
                    Font(R.font.nunitosans_7pt_condensed_light)
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .size(60.dp)
                        .shadow(elevation = 2.dp, RoundedCornerShape(8.dp))
                        .background(color = Color("#F5F5F5".toColorInt()))
                        .clickable(onClick = {
                            viewmodel.add_to_favorite(
                                Product(
                                    prName = name,
                                    prPrice = price,
                                    prImg = image,
                                    rate = rate,
                                    description = description,
                                    quantity = quantity,
                                    prId = id
                                )
                            )
                            Toast.makeText(context, "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show()
                        }),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.marker),
                        contentDescription = null,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(7.dp)
                        .width(270.dp)
                        .height(60.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF242424))
                        .clickable(onClick = {
                            viewmodel.add_to_cart(product = Product(
                                prName = name,
                                prPrice = price,
                                prImg = image,
                                rate = rate,
                                description = description,
                                quantity = selectedQuantity.value,
                                prId = id
                            ))
                            Toast.makeText(context, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show()
                            navController.navigate("cart")
                        })
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Add to cart",
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.nunitosans_7pt_condensed_light)),
                            fontWeight = FontWeight(600),
                            fontSize = 17.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Custom(navController: NavController, image: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(390.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box {}
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    modifier = Modifier
                        .width(330.dp)
                        .fillMaxHeight()
                        .shadow(
                            elevation = 2.dp,
                            shape = RoundedCornerShape(bottomStart = 52.dp)
                        )
                        .zIndex(1f),
                    contentScale = ContentScale.FillBounds
                )
            }
        }
        Box(
            modifier = Modifier
                .width(130.dp)
                .fillMaxHeight()
        ) {
            Column (modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally){
                Row(
                    modifier = Modifier
                        .size(45.dp)
                        .clickable { navController.navigateUp() }
                        .background(color = Color.White, RoundedCornerShape(14.dp))
                        .shadow(
                            elevation = 0.dp,
                            shape = RoundedCornerShape(14.dp),
                            clip = true
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.arrowback),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Column (modifier = Modifier
                    .height(192.dp)
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(40.dp),
                        clip = true
                    )
                    .width(64.dp)
                    .background(Color.White, shape = RoundedCornerShape(40.dp)),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Image(painter = painterResource(id = R.drawable.color1), contentDescription = null, modifier = Modifier.size(34.dp))
                    Image(painter = painterResource(id = R.drawable.color2), contentDescription = null, modifier = Modifier.size(34.dp))
                    Image(painter = painterResource(id = R.drawable.color3), contentDescription = null, modifier = Modifier.size(34.dp))
                }

                Row{

                }
            }

        }
    }
}

