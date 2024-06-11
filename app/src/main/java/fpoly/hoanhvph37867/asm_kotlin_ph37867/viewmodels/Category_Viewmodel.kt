package fpoly.hoanhvph37867.asm_kotlin_ph37867.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fpoly.hoanhvph37867.asm_kotlin_ph37867.api_service.Api_service
import fpoly.hoanhvph37867.asm_kotlin_ph37867.models.Category
import kotlinx.coroutines.launch

class Category_Viewmodel: ViewModel() {
    private val apiService = Api_service
    val categories: MutableState<List<Category>> = mutableStateOf(emptyList())
    fun get_category() {
        viewModelScope.launch {
            try {
                val response = apiService.GET_CATEGORY()
                categories.value = response
                Log.e("list_cate", "success")
            } catch (e: Exception) {
                Log.e("list_cate", e.message.toString())
            }
        }
    }
}