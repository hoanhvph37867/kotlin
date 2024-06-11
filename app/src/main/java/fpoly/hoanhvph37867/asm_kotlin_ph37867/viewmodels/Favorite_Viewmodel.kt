package fpoly.hoanhvph37867.asm_kotlin_ph37867.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fpoly.hoanhvph37867.asm_kotlin_ph37867.api_service.Api_service
import fpoly.hoanhvph37867.asm_kotlin_ph37867.models.Favorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Favorite_Viewmodel: ViewModel() {
    private val _apiService = Api_service
    val _favorite: MutableState<List<Favorite>> = mutableStateOf(emptyList())
    fun getFavorite() {
        viewModelScope.launch {
            try {
                _favorite.value = _apiService.GET_FAVORITE()
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }
    fun deleteItemFav(item: Favorite) {
        _favorite.value = _favorite.value.filter { it != item }
        viewModelScope.launch {
            try {
                _apiService.DELETE_FAVORITE(item.id)
                Log.e("Cart", "Delete Success")
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }
    private val _isAddtoCart = mutableStateOf(false)
    fun add_to_cart(item: Favorite) {
        _isAddtoCart.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val addToFavoriteResponse = _apiService.POST_CART_IN_FAV(item)
                if (addToFavoriteResponse.isSuccessful){
                    Log.d("add_to_favorite", "Product added to favorite successfully!")
                }else{
                    Log.e("add_to_favorite", "Error adding product to favorite: ${addToFavoriteResponse.code()}")
                }
            }catch (e: Exception) {
                Log.e("add_to_favorite", e.message.toString())
            }finally {
                _isAddtoCart.value = false
            }
        }
    }
}