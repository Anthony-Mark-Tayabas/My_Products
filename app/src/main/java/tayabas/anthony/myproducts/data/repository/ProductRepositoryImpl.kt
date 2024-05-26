package tayabas.anthony.myproducts.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import tayabas.anthony.myproducts.data.remote.api.ProductApi
import tayabas.anthony.myproducts.data.remote.dto.ProductResponse
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productApi: ProductApi
) : ProductRepository {
    override suspend fun getProducts(): Response<ProductResponse> {
        return withContext(Dispatchers.IO) {
            productApi.getProducts()
        }
    }
}