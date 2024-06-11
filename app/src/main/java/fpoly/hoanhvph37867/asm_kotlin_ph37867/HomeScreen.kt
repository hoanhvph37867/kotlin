package fpoly.hoanhvph37867.asm_kotlin_ph37867

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import fpoly.hoanhvph37867.asm_kotlin_ph37867.models.Category
import fpoly.hoanhvph37867.asm_kotlin_ph37867.models.Product
import fpoly.hoanhvph37867.asm_kotlin_ph37867.viewmodels.Category_Viewmodel
import fpoly.hoanhvph37867.asm_kotlin_ph37867.viewmodels.Favorite_Viewmodel
import fpoly.hoanhvph37867.asm_kotlin_ph37867.viewmodels.Product_Viewmodel

enum class ROUTE_HOME_SCREEN {
    Home,
    Favorite,
    Notification,
    Profile
}


@Composable
fun FurnitureApp(navHostController : NavController, viewModel: Favorite_Viewmodel) {
    val navController = rememberNavController()
    val items = listOf(
        BottomNavigationItem(ROUTE_HOME_SCREEN.Home.name, Icons.Default.Home, Icons.Outlined.Home),
        BottomNavigationItem(ROUTE_HOME_SCREEN.Favorite.name, Icons.Default.Favorite, Icons.Outlined.Favorite),
        BottomNavigationItem(
            "Notification",
            Icons.Default.Notifications,
            Icons.Outlined.Notifications
        ),
        BottomNavigationItem(ROUTE_HOME_SCREEN.Profile.name, Icons.Default.Person, Icons.Outlined.Person)
    )
    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                TopAppBar(navController = navController , navHostController = navHostController)
            },
            bottomBar = {
                BottomNavigationBar(
                    items = items,
                    selectedItemIndex = selectedItemIndex,
                    onItemSelected = { index ->
                        selectedItemIndex = index
                        navController.navigate(items[index].title)
                    }
                )
            }
        ) { innerPadding ->
            NavigationGraph(navHostController = navHostController,navController = navController, innerPadding = innerPadding, viewModel = viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(navController: NavHostController, navHostController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "Home"
    val homeTitle = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Color.Gray,
                fontSize = 18.sp,
                fontWeight = FontWeight(400),
                fontFamily = FontFamily(Font(R.font.gelasio_bold))
            )
        ) {
            append("Make home\n")
        }
        withStyle(
            style = SpanStyle(
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight(700),
                fontFamily = FontFamily(Font(R.font.gelasio_bold))
            )
        ) {
            append("BEAUTIFUL")
        }
    }

    val title: Any = when (currentRoute) {
        "Home" -> homeTitle
        "Favorite" -> "Favorite"
        "Notification" -> "Notification"
        "Profile" -> "Profile"
        else -> "Furniture App"
    }
    androidx.compose.material3.TopAppBar(
        title = {
            if (title is AnnotatedString) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            } else if (title is String) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontFamily = FontFamily(
                        Font(R.font.merriweather_regular)
                    )
                )
            }
        },
        actions = {
            IconButton(onClick = { navHostController.navigate("cart") }) {
                Icon(
                    painter = painterResource(id = R.drawable.cart),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
    )
}

@Composable
fun BottomNavigationBar(
    items: List<BottomNavigationItem>,
    selectedItemIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    NavigationBar(
        containerColor = Color("#ffffff".toColorInt())
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = { onItemSelected(index) },
                icon = {
                    Icon(
                        imageVector = if (selectedItemIndex == index) item.selectIcon else item.unselectItem,
                        contentDescription = item.title,
                        modifier = Modifier.size(24.dp)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorResource(id = android.R.color.black),
                    unselectedIconColor = Color.Gray
                )
            )
        }
    }
}
@Composable
fun ListCategory(viewmodel: Category_Viewmodel) {
    val category = viewmodel.categories.value
    val selectedItemIndex = remember { mutableStateOf(-1) }
    LazyRow {
        items(category.size) { index ->
            val item = category[index]
            val isSelected = selectedItemIndex.value == index

            CategoryItem(
                item = item,
                isSelected = isSelected,
                onClick = { selectedItemIndex.value = index }
            )
        }
    }
    viewmodel.get_category()
}
@Composable
fun CategoryItem(item: Category, isSelected: Boolean, onClick: () -> Unit) {

    val backgroundColor = if (isSelected) Color.Black else Color("#F5F5F5".toColorInt())
    val textColor = if (isSelected) Color.Black else Color("#999999".toColorInt())

    Column(
        modifier = Modifier.padding(end = 25.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .size(44.dp)
                .shadow(elevation = 2.dp, RoundedCornerShape(14.dp))
                .background(color = backgroundColor)
                .clickable(onClick = onClick),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            AsyncImage(
                model = item.cateImg,
                contentDescription = null,
                modifier = Modifier.size(25.dp),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = item.cateName,
            color = textColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(
                Font(R.font.nunitosans_7pt_condensed_light)
            )
        )
    }
}
@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.padding(
            top = 10.dp,
            end = 15.dp,
            start = 15.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            ListCategory(viewmodel = Category_Viewmodel())
        }
        Spacer(modifier = Modifier.height(10.dp))
        ProductList(viewModel = Product_Viewmodel(), navController = navController)
    }
}


@Composable
fun NavigationGraph(navHostController : NavController, navController: NavHostController, innerPadding: PaddingValues, viewModel: Favorite_Viewmodel) {
    NavHost(
        navController,
        startDestination = ROUTE_HOME_SCREEN.Home.name,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(ROUTE_HOME_SCREEN.Home.name) { HomeScreen(navHostController) }
        composable(ROUTE_HOME_SCREEN.Favorite.name) { FavoriteScreen(innerPadding, viewModel) }
        composable(ROUTE_HOME_SCREEN.Notification.name) { NotificationScreen(innerPadding) }
        composable(ROUTE_HOME_SCREEN.Profile.name) { AccountScreenControl(innerPadding , navHostController) }
    }
}

data class BottomNavigationItem(
    val title: String,
    val selectIcon: ImageVector,
    var unselectItem: ImageVector
)



@Composable
fun ProductItem(navController: NavController, item: Product) {
    Column(
        modifier = Modifier
            .width(165.dp)
            .height(253.dp)
            .clickable {
                navController.navigate(
                    "detail/" +
                            "${Uri.encode(item.prId)}/" +
                            "${Uri.encode(item.prName)}/" +
                            "${Uri.encode(item.prImg)}/" +
                            "${Uri.encode(item.prPrice)}/" +
                            "${Uri.encode(item.description)}/" +
                            "${Uri.encode(item.rate)}"
                )
            },
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = item.prImg,
                contentDescription = null
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 10.dp, end = 15.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End
            ) {
                Row {}
                Row(
                    modifier = Modifier
                        .size(30.dp)
                        .shadow(elevation = 2.dp, RoundedCornerShape(6.dp))
                        .background(color = Color("#95a5a6".toColorInt()))
                        .alpha(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.shopping_bag),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
        Text(
            text = item.prName,
            fontSize = 14.sp,
            color = Color.Gray,
            fontWeight = FontWeight(500),
            fontFamily = FontFamily(
                Font(R.font.nunitosans_7pt_condensed_light)
            )
        )
        Text(
            text = "\$ " + item.prPrice,
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(
                Font(R.font.nunitosans_7pt_condensed_light)
            )
        )
    }
}
@Composable
fun ProductList(viewModel: Product_Viewmodel, navController: NavController) {
    val product = viewModel.products.value
    val gridState = rememberLazyStaggeredGridState()
    Box(
        modifier = Modifier
    ) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            state = gridState,
            verticalItemSpacing = 10.dp,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(10.dp)
        ) {
            items(product.size) { item ->
                ProductItem(item = product[item], navController = navController)
            }
        }
    }

    viewModel.get_products()
}