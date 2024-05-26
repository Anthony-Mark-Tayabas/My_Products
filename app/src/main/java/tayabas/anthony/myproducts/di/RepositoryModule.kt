package tayabas.anthony.myproducts.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tayabas.anthony.myproducts.data.remote.api.ProductApi
import tayabas.anthony.myproducts.data.repository.ProductRepository
import tayabas.anthony.myproducts.data.repository.ProductRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideProductRepository(
        productApi: ProductApi
    ): ProductRepository {
        return ProductRepositoryImpl(productApi)
    }

}