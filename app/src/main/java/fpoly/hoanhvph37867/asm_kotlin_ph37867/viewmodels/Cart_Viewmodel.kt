package fpoly.hoanhvph37867.asm_kotlin_ph37867.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fpoly.hoanhvph37867.asm_kotlin_ph37867.api_service.Api_service
import fpoly.hoanhvph37867.asm_kotlin_ph37867.models.Cart
import kotlinx.coroutines.launch

class Cart_Viewmodel : ViewModel() {
    private val _apiService = Api_service
    val _cart: MutableState<List<Cart>> = mutableStateOf(emptyList())
    val cart: State<List<Cart>> = _cart
    fun getCart() {
        viewModelScope.launch {
            try {
                _cart.value = _apiService.GET_CART()
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun deleteItemCart(item: Cart) {
        _cart.value = _cart.value.filter { it != item }
        viewModelScope.launch {
            try {
                _apiService.DELETE_CART(item.id)
                Log.e("Cart", "Delete Success")
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    // Hàm cập nhật số lượng trong cơ sở dữ liệu
    private fun updateQuantity(item: Cart, quantity: Int) {
        viewModelScope.launch {
            try {
                _apiService.updateCartItemQuantity(item.id, quantity)
                Log.e("Cart", "Update Quantity Success")
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    // Hàm thêm sản phẩm vào cơ sở dữ liệu
    private fun addToDatabase(item: Cart, quantity: Int) {
        viewModelScope.launch {
            try {
                _apiService.ADD_TO_CART(item, quantity)
                Log.e("Cart", "Add to Database Success")
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    // Hàm xoá sản phẩm khỏi cơ sở dữ liệu
    private fun removeFromDatabase(item: Cart) {
        viewModelScope.launch {
            try {
                _apiService.DELETE_CART(item.id)
                Log.e("Cart", "Remove from Database Success")
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    // Hàm thêm sản phẩm vào giỏ hàng
    fun addToCart(item: Cart, quantity: Int) {
        val updatedCart = _cart.value.toMutableList()
        val existingItemIndex = updatedCart.indexOfFirst { it.id == item.id }
        if (existingItemIndex != -1) {
            // Nếu sản phẩm đã có trong giỏ hàng, cập nhật số lượng
            updatedCart[existingItemIndex].quantity = quantity
            // Cập nhật số lượng trong cơ sở dữ liệu
            updateQuantity(item, quantity)
        } else {
            // Nếu sản phẩm chưa có trong giỏ hàng, thêm vào giỏ hàng với số lượng mới
            updatedCart.add(item)
            updatedCart.last().quantity = quantity
            // Thêm sản phẩm vào cơ sở dữ liệu
            addToDatabase(item, quantity)
        }
        // Cập nhật giỏ hàng mới
        _cart.value = updatedCart
    }

    // Hàm xoá sản phẩm khỏi giỏ hàng
    fun removeFromCart(item: Cart, quantity: Int) {
        val updatedCart = _cart.value.toMutableList()
        val existingItemIndex = updatedCart.indexOfFirst { it.id == item.id }
        if (existingItemIndex != -1) {
            // Nếu số lượng mới là 0, xoá sản phẩm khỏi giỏ hàng
            if (quantity == 0) {
                updatedCart.removeAt(existingItemIndex)
                // Xoá sản phẩm khỏi cơ sở dữ liệu
                removeFromDatabase(item)
            } else {
                // Nếu không, cập nhật số lượng mới
                updatedCart[existingItemIndex].quantity = quantity
                // Cập nhật số lượng trong cơ sở dữ liệu
                updateQuantity(item, quantity)
            }
            // Cập nhật giỏ hàng mới
            _cart.value = updatedCart
        }
    }


    fun checkOut() {
        viewModelScope.launch {
            try {
                for (item in _cart.value) {
                    val response = _apiService.POST_HISTORY(item)
                    if (response.isSuccessful) {
                        _cart.value = emptyList()
                        Log.e("Cart", "Check Out Success")
                    } else {
                        Log.e("Cart", "Check Out Failed")
                    }
                }
            } catch (e: Exception) {
                println("Cart: ${e.message}")
            }
        }
    }
}