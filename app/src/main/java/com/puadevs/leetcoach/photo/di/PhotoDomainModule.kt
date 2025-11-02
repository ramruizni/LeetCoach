package com.puadevs.leetcoach.photo.di

import com.puadevs.leetcoach.photo.domain.PhotoRepository
import com.puadevs.leetcoach.photo.domain.usecases.RetrieveTextFrom
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PhotoDomainModule {

    @Singleton
    @Provides
    fun provideRetrieveTextFrom(
        photoRepository: PhotoRepository
    ): RetrieveTextFrom = RetrieveTextFrom(
        photoRepository = photoRepository
    )
}