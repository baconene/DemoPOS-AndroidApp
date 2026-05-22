package com.demopos.di

import android.content.Context
import androidx.room.Room
import com.demopos.data.local.database.DemoPOSDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDemoPOSDatabase(
        @ApplicationContext context: Context
    ): DemoPOSDatabase {
        return Room.databaseBuilder(
            context,
            DemoPOSDatabase::class.java,
            "demopos_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(database: DemoPOSDatabase) = database.userDao()

    @Singleton
    @Provides
    fun provideAuthSessionDao(database: DemoPOSDatabase) = database.authSessionDao()

    @Singleton
    @Provides
    fun provideProductDao(database: DemoPOSDatabase) = database.productDao()

    @Singleton
    @Provides
    fun provideCategoryDao(database: DemoPOSDatabase) = database.categoryDao()

    @Singleton
    @Provides
    fun provideInventoryDao(database: DemoPOSDatabase) = database.inventoryDao()

    @Singleton
    @Provides
    fun provideOrderDao(database: DemoPOSDatabase) = database.orderDao()

    @Singleton
    @Provides
    fun provideOrderItemDao(database: DemoPOSDatabase) = database.orderItemDao()

    @Singleton
    @Provides
    fun providePaymentDao(database: DemoPOSDatabase) = database.paymentDao()

    @Singleton
    @Provides
    fun provideCustomerDao(database: DemoPOSDatabase) = database.customerDao()

    @Singleton
    @Provides
    fun provideReceiptDao(database: DemoPOSDatabase) = database.receiptDao()

    @Singleton
    @Provides
    fun provideSyncQueueDao(database: DemoPOSDatabase) = database.syncQueueDao()

    @Singleton
    @Provides
    fun provideActivityLogDao(database: DemoPOSDatabase) = database.activityLogDao()
}
