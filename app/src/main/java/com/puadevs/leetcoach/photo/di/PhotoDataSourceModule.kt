package com.puadevs.leetcoach.photo.di

import android.content.Context
import com.puadevs.leetcoach.photo.datasource.PhotoDataSourceImpl
import com.puadevs.leetcoach.photo.repository.PhotoDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PhotoDataSourceModule {

    @Singleton
    @Provides
    fun providePhotoDataSource(
        @ApplicationContext context: Context
    ): PhotoDataSource = PhotoDataSourceImpl(
        context = context.applicationContext
    )
}