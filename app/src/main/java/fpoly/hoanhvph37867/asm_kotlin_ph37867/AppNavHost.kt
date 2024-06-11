package fpoly.hoanhvph37867.asm_kotlin_ph37867

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fpoly.hoanhvph37867.asm_kotlin_ph37867.viewmodels.Favorite_Viewmodel
import fpoly.hoanhvph37867.asm_kotlin_ph37867.viewmodels.Product_Viewmodel
import fpoly.hoanhvph37867.asm_kotlin_ph37867.viewmodels.User_Viewmodel


enum class ROUTE_NAME {
    welcome,
    login,
    home,
    signup,
    detail,
    cart,
    checkout,
    success,
    order,
    addShipment,
    addPayment,
    paymentMethod,
    setting,
    selectShipment,
    myReview,
    rating
}

@Composable
fun AppNavHost(
    navController: NavHostController
) {

    NavHost(navController = navController, startDestination = ROUTE_NAME.welcome.name) {
        composable(ROUTE_NAME.welcome.name) { WelComeScreen(navController) }
        composable(ROUTE_NAME.login.name) { LoginScreen(navController, User_Viewmodel()) }
        composable(ROUTE_NAME.home.name) { FurnitureApp(navController, Favorite_Viewmodel()) }
        composable(ROUTE_NAME.signup.name) { RegisterScreen(navController, User_Viewmodel()) }
        composable("${ROUTE_NAME.detail.name}/{prId}/{prName}/{prImg}/{prPrice}/{description}/{rate}") {
            DetailsProduct(
                navController,
                viewmodel = Product_Viewmodel(),
                id = it.arguments?.getString("prId").toString(),
                name = it.arguments?.getString("prName").toString(),
                image = it.arguments?.getString("prImg").toString(),
                rate = it.arguments?.getString("rate").toString(),
                price = it.arguments?.getString("prPrice").toString(),
                quantity = it.arguments?.getString("quantity")?.toIntOrNull() ?: 0,
                description = it.arguments?.getString("description").toString()
            )
        }
        composable(ROUTE_NAME.cart.name) { SmallTopAppCart(navController) }
        composable(ROUTE_NAME.checkout.name) { SmallTopAppBarPayment(navController) }
        composable(ROUTE_NAME.success.name) { FinalScreen(navController) }
        composable(ROUTE_NAME.order.name) { OrderScreenRun(navController) }
        composable(ROUTE_NAME.addShipment.name) { AddShipmentScreen(navController) }
        composable(ROUTE_NAME.addPayment.name) { AddPaymentScreen(navController) }
        composable(ROUTE_NAME.paymentMethod.name) { SelectPaymentScreen(navController) }
        composable(ROUTE_NAME.setting.name) { settingScreens(navController) }
        composable(ROUTE_NAME.selectShipment.name) { AddressScreen(navController) }
        composable(ROUTE_NAME.myReview.name) { MyReViewTopBar(navController) }
        composable(ROUTE_NAME.rating.name) { ReView(navController) }
    }
}


