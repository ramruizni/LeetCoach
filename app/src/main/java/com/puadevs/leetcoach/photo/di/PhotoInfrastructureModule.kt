package com.puadevs.leetcoach.photo.di

import com.puadevs.leetcoach.photo.domain.PhotoRepository
import com.puadevs.leetcoach.photo.repository.PhotoDataSource
import com.puadevs.leetcoach.photo.repository.PhotoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PhotoInfrastructureModule {

    @Singleton
    @Provides
    fun providePhotoRepository(
        photoDataSource: PhotoDataSource
    ): PhotoRepository = PhotoRepositoryImpl(
        photoDataSource = photoDataSource
    )
}