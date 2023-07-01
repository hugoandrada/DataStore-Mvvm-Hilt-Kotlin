package com.hugo.andrada.dev.datastore.di

import android.content.Context
import com.hugo.andrada.dev.datastore.data.repository.DataStoreRepositoryImpl
import com.hugo.andrada.dev.datastore.domain.repository.DataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStoreRepository {
        return DataStoreRepositoryImpl(context = context)
    }
}