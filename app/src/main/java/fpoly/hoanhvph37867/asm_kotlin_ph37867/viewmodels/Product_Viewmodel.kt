package fpoly.hoanhvph37867.asm_kotlin_ph37867.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fpoly.hoanhvph37867.asm_kotlin_ph37867.api_service.Api_service
import fpoly.hoanhvph37867.asm_kotlin_ph37867.models.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Product_Viewmodel: ViewModel() {
    private val apiService = Api_service
    val products: MutableState<List<Product>> = mutableStateOf(emptyList())
    fun get_products() {
        viewModelScope.launch {
            try {
                val response = apiService.GET_PRODUCT()
                products.value = response
                Log.e("list_product", "success")
            } catch (e: Exception) {
                Log.e("list_product", e.message.toString())
            }
        }
    }

    private val _isAddtoCart = mutableStateOf(false)
    val isAddtoCart: MutableState<Boolean> = _isAddtoCart
    fun add_to_cart(product: Product) {
        _isAddtoCart.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val addToCartResponse = apiService.POST_CART(product) // Example API call
                if (addToCartResponse.isSuccessful) {
                    // Handle successful addition (e.g., update UI, confirmation message)
                    Log.d("add_to_cart", "Product added to cart successfully!")
                } else {
                    // Handle error (e.g., display error message to user)
                    Log.e("add_to_cart", "Error adding product to cart: ${addToCartResponse.code()}")
                }
            }catch (e: Exception) {
                Log.e("add_to_cart", e.message.toString())
            }finally {
                _isAddtoCart.value = false // Set adding state to false
            }
        }
    }
    private val _isAddtoFavorite = mutableStateOf(false)
    fun add_to_favorite(product: Product) {
        _isAddtoFavorite.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val addToFavoriteResponse = apiService.POST_FAVORITE(product)
                if (addToFavoriteResponse.isSuccessful){
                    Log.d("add_to_favorite", "Product added to favorite successfully!")
                }else{
                    Log.e("add_to_favorite", "Error adding product to favorite: ${addToFavoriteResponse.code()}")
                }
            }catch (e: Exception) {
                Log.e("add_to_favorite", e.message.toString())
            }finally {
                _isAddtoFavorite.value = false
            }
        }
    }
}