package com.puadevs.leetcoach.voicetext.di

import com.puadevs.leetcoach.voicetext.domain.VoiceTextRepository
import com.puadevs.leetcoach.voicetext.domain.usecases.RetrieveVoiceTextFrom
import com.puadevs.leetcoach.voicetext.domain.usecases.StartRecording
import com.puadevs.leetcoach.voicetext.domain.usecases.StopRecording
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VoiceTextDomainModule {

    @Singleton
    @Provides
    fun provideRetrieveVoiceTextFrom(
        voiceTextRepository: VoiceTextRepository
    ): RetrieveVoiceTextFrom = RetrieveVoiceTextFrom(
        voiceTextRepository = voiceTextRepository
    )

    @Singleton
    @Provides
    fun provideStartRecording(
        voiceTextRepository: VoiceTextRepository
    ): StartRecording = StartRecording(
        voiceTextRepository = voiceTextRepository
    )

    @Singleton
    @Provides
    fun provideStopRecording(
        voiceTextRepository: VoiceTextRepository
    ): StopRecording = StopRecording(
        voiceTextRepository = voiceTextRepository
    )
}