package tayabas.anthony.myproducts.data.repository

import retrofit2.Response
import tayabas.anthony.myproducts.data.remote.dto.ProductResponse

interface ProductRepository {
    suspend fun getProducts(): Response<ProductResponse>
}