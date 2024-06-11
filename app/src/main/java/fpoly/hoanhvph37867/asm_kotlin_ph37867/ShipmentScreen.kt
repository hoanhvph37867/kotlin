package fpoly.hoanhvph37867.asm_kotlin_ph37867

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController

class ShipmentScreen : ComponentActivity() {
}
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    @Preview
    fun AddressScreen(navController : NavController) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Shipping address",
                            textAlign = TextAlign.Center,
                            fontSize = 17.sp,
                            modifier = Modifier.fillMaxWidth(),
                            fontFamily = FontFamily(
                                Font(R.font.merriweather_regular)
                            )
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                painter = painterResource(id = R.drawable.arrowback),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            modifier = Modifier.size(24.dp),
                            onClick = { /* Handle action */ }) {
                            // Action icon (e.g., settings or more options) can be added here
                        }
                    },
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navController.navigate("addShipment")
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add"
                    )
                }
            },
            content = { innerPadding ->
                SelectAddress(innerPadding)
            }
        )
    }

    @Composable
    @Preview(showBackground = true)
    fun SelectAddress(innerPaddingValues: PaddingValues){
        Column (modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.background))
            .padding(start = 10.dp, innerPaddingValues.calculateTopPadding(), end = 10.dp)){
            LazyColumn{
                items(3) {item ->
                    ItemAddress()
                }
            }
        }
    }
    @Composable
    @Preview
    fun ItemAddress(){
        Column (modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(Color.White),
            verticalArrangement = Arrangement.SpaceBetween){
            Row (modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                Checkbox(checked = false, onCheckedChange = {})
                Text(text = "Use as the shipping address",
                    fontSize = 18.sp, fontWeight = FontWeight(400)
                )
            }
            Column (modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .background(Color("#ffffff".toColorInt()))
                .shadow(0.dp, RoundedCornerShape(8.dp), clip = true),
                verticalArrangement = Arrangement.SpaceEvenly){
               Row (modifier = Modifier
                   .fillMaxWidth()
                   .padding(start = 15.dp, end = 10.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
                   Text(text = "Hoàng Văn Hoàn", fontSize = 20.sp, fontWeight = FontWeight(700), fontFamily = FontFamily(
                       Font(R.font.nunitosans_7pt_condensed_bold)
                   ))
                   Image(painter = painterResource(id = R.drawable.edit), modifier = Modifier.size(24.dp), contentDescription = null)
               }
                Divider(
                    color = Color.Gray,
                    thickness = 1.5.dp)

                Column (modifier = Modifier.padding(start = 15.dp)){
                    Text(text = "Số 1, Ngõ 2, Đường 3, Quận 4, Thành Phố 5", color = Color.Gray, fontSize = 15.sp, fontFamily = FontFamily(
                        Font(R.font.nunitosans_7pt_condensed_bold)
                    ), fontWeight = FontWeight(500)
                    )
                    Text(text = "Nhanh là được"
                        , fontSize = 15.sp,color = Color.Gray, fontFamily = FontFamily(
                            Font(R.font.nunitosans_7pt_condensed_bold)))
                }
            }
        }
    }