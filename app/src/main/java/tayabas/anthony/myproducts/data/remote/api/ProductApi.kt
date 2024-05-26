package tayabas.anthony.myproducts.data.remote.api

import retrofit2.Response
import retrofit2.http.GET
import tayabas.anthony.myproducts.data.remote.dto.ProductResponse

interface ProductApi {

    @GET("/products")
    suspend fun getProducts(): Response<ProductResponse>

}