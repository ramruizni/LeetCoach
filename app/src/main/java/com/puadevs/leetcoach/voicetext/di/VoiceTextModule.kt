package com.puadevs.leetcoach.voicetext.di

import com.google.gson.GsonBuilder
import com.puadevs.leetcoach.BuildConfig
import com.puadevs.leetcoach.voicetext.Constants.AI_BASE_URL_OPEN_AI
import com.puadevs.leetcoach.voicetext.datasource.VoiceDataSourceImpl
import com.puadevs.leetcoach.voicetext.datasource.remote.WhisperApi
import com.puadevs.leetcoach.voicetext.domain.VoiceTextRepository
import com.puadevs.leetcoach.voicetext.domain.usecases.RetrieveVoiceTextFrom
import com.puadevs.leetcoach.voicetext.domain.usecases.StartRecording
import com.puadevs.leetcoach.voicetext.domain.usecases.StopRecording
import com.puadevs.leetcoach.voicetext.repository.VoiceTextDataSource
import com.puadevs.leetcoach.voicetext.repository.VoiceTextRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VoiceTextModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class VoiceTextRetrofit

    @Singleton
    @Provides
    @VoiceTextRetrofit
    fun provideVoiceTextOkHttpClient(): OkHttpClient {
        val loggingInterceptor =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${BuildConfig.API_KEY_OPEN_AI}")
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    @VoiceTextRetrofit
    fun provideVoiceTextRetrofit(
        @VoiceTextRetrofit okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AI_BASE_URL_OPEN_AI)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    @Singleton
    @Provides
    fun provideWhisperApi(@VoiceTextRetrofit retrofit: Retrofit): WhisperApi {
        return retrofit.create(WhisperApi::class.java)
    }

    @Singleton
    @Provides
    fun provideVoiceTextDataSource(
        whisperApi: WhisperApi
    ): VoiceTextDataSource = VoiceDataSourceImpl(
        whisperApi = whisperApi
    )

    @Singleton
    @Provides
    fun provideVoiceTextRepository(
        voiceTextDataSource: VoiceTextDataSource
    ): VoiceTextRepository = VoiceTextRepositoryImpl(
        voiceTextDataSource = voiceTextDataSource
    )

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