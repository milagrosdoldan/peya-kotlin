package com.example.peyaapp

import android.content.Context
import androidx.room.Room
import com.example.peyaapp.model.data.local.SessionManager
import com.example.peyaapp.model.database.PeyaDatabase
import com.example.peyaapp.model.database.dao.OrderDao
import com.example.peyaapp.model.database.dao.ProductDao
import com.example.peyaapp.model.repository.cart.CartDataSource
import com.example.peyaapp.model.repository.cart.CartDataSourceImpl
import com.example.peyaapp.model.repository.login.LoginDataSource
import com.example.peyaapp.model.repository.login.LoginDataSourceImpl
import com.example.peyaapp.model.repository.orders.OrderDataSource
import com.example.peyaapp.model.repository.orders.OrderDataSourceImpl
import com.example.peyaapp.model.repository.product.ProductDataSource
import com.example.peyaapp.model.repository.product.ProductDataSourceImpl
import com.example.peyaapp.model.repository.profile.ProfileDataSource
import com.example.peyaapp.model.repository.profile.ProfileDataSourceImpl
import com.example.peyaapp.model.repository.register.RegisterDataSource
import com.example.peyaapp.model.repository.register.RegisterDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppProvidesModule {


    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PeyaDatabase {
        return Room.databaseBuilder(
            context,
            PeyaDatabase::class.java,
            "peya_db"
        ).build()
    }


    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager {
        return SessionManager(context)
    }

    @Provides
    fun providesProfileDataSource(): ProfileDataSource {
        return ProfileDataSourceImpl()
    }

    @Provides
    fun provideProductDao(database: PeyaDatabase): ProductDao {
        return database.productDao()
    }

    @Provides
    fun provideOrderDao(database: PeyaDatabase): OrderDao {
        return database.orderDao()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppBindsModule {

    @Binds
    @Singleton
    abstract fun bindLoginDataSource(
        loginDataSourceImpl: LoginDataSourceImpl
    ): LoginDataSource

    @Binds
    abstract fun bindRegisterDataSource(
        impl: RegisterDataSourceImpl
    ): RegisterDataSource

    @Binds
    @Singleton
    abstract fun bindProductDataSource(
        impl: ProductDataSourceImpl
    ): ProductDataSource

    @Binds
    @Singleton
    abstract fun bindCartItemDataSource(
        impl: CartDataSourceImpl
    ): CartDataSource

    @Binds
    @Singleton
    abstract fun bindOrderDataSource(
        impl: OrderDataSourceImpl
    ): OrderDataSource

}
