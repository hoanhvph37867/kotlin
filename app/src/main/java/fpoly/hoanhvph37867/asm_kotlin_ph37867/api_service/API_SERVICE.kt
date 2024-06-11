package fpoly.hoanhvph37867.asm_kotlin_ph37867.api_service

import com.google.gson.GsonBuilder
import fpoly.hoanhvph37867.asm_kotlin_ph37867.models.Cart
import fpoly.hoanhvph37867.asm_kotlin_ph37867.models.Category
import fpoly.hoanhvph37867.asm_kotlin_ph37867.models.Favorite
import fpoly.hoanhvph37867.asm_kotlin_ph37867.models.Product
import fpoly.hoanhvph37867.asm_kotlin_ph37867.models.User

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

var BASE_URL = "http://10.0.2.2:3000/"
//var BASE_URL = "http://localhost:3000/"
var gson = GsonBuilder().create()
var Api_service = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .build()
    .create<API_SERVICE>(API_SERVICE::class.java)
interface API_SERVICE {
    //Product
    @GET("Product")
    suspend fun GET_PRODUCT(): List<Product>
    @GET("Category")
    suspend fun GET_CATEGORY(): List<Category>

    @POST("Cart")
    suspend fun POST_CART(@Body product: Product): Response<Unit>
    @POST("Cart")
    suspend fun POST_CART_IN_FAV(@Body favorite: Favorite): Response<Unit>
    @GET("Cart")
    suspend fun GET_CART(): List<Cart>
    @DELETE("Cart/{id}")
    suspend fun DELETE_CART(@Path("id") id: String): Response<Unit>
    @PUT("Cart/{id}")
    suspend fun updateCartItemQuantity(@Path("id") id: String, @Body quantity: Int): Response<Unit>
    @PUT("Cart/{id}")
    suspend fun UPDATE_CART(@Path("id") id: String, @Body quantity: Int): Response<Unit>
    @GET("products/{productId}")
    suspend fun getProduct(@Path("productId") productId: String): Product

    @PUT("products/{productId}")
    suspend fun updateProductQuantity(
        @Path("productId") productId: String,
        @Body updatedQuantity: Int
    ): Response<Unit>

    @POST("Cart")
    suspend fun ADD_TO_CART(@Body item: Cart, @Body quantity: Int): Response<Unit>

    @POST("History")
    suspend fun POST_HISTORY(@Body cart: Cart): Response<Unit>

    @POST("User")
    suspend fun REGISTER(@Body user: User): Response<Unit>
    @GET("User")
    suspend fun GET_USER(): List<User>

    @POST("Favorite")
    suspend fun POST_FAVORITE(@Body product: Product): Response<Unit>
    @GET("Favorite")
    suspend fun GET_FAVORITE(): List<Favorite>
    @DELETE("Favorite/{id}")
    suspend fun DELETE_FAVORITE(@Path("id") id: String): Response<Unit>

}