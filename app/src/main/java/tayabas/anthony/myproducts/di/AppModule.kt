package tayabas.anthony.myproducts.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tayabas.anthony.myproducts.MyProductsApplication
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): MyProductsApplication {
        return app as MyProductsApplication
    }

    @Singleton
    @Provides
    fun provideContext(application: MyProductsApplication): Context {
        return application.applicationContext
    }
}