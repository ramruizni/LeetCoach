package com.puadevs.leetcoach.voicetext.di

import com.puadevs.leetcoach.voicetext.domain.VoiceTextRepository
import com.puadevs.leetcoach.voicetext.repository.VoiceTextDataSource
import com.puadevs.leetcoach.voicetext.repository.VoiceTextRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VoiceTextInfrastructureModule {

    @Singleton
    @Provides
    fun provideVoiceTextRepository(
        voiceTextDataSource: VoiceTextDataSource
    ): VoiceTextRepository = VoiceTextRepositoryImpl(
        voiceTextDataSource = voiceTextDataSource
    )
}