package com.puadevs.leetcoach.chat.di

import com.google.gson.GsonBuilder
import com.puadevs.leetcoach.BuildConfig
import com.puadevs.leetcoach.chat.Constants.CHAT_API_BASE_URL
import com.puadevs.leetcoach.chat.datasource.ChatDataSourceImpl
import com.puadevs.leetcoach.chat.datasource.remote.ChatApi
import com.puadevs.leetcoach.chat.domain.ChatRepository
import com.puadevs.leetcoach.chat.domain.usecases.SendMessage
import com.puadevs.leetcoach.chat.repository.ChatDataSource
import com.puadevs.leetcoach.chat.repository.ChatRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChatModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ChatRetrofit

    @Singleton
    @Provides
    @ChatRetrofit
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(CHAT_API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    @Singleton
    @Provides
    fun provideLLMAPI(@ChatRetrofit retrofit: Retrofit): ChatApi =
        retrofit.create(ChatApi::class.java)

    @Singleton
    @Provides
    fun provideChatDataSource(
        api: ChatApi
    ): ChatDataSource = ChatDataSourceImpl(
        api = api,
        apiKey = BuildConfig.API_KEY_OPEN_ROUTER,
    )

    @Singleton
    @Provides
    fun provideChatRepository(
        chatDataSource: ChatDataSource
    ): ChatRepository = ChatRepositoryImpl(
        chatDataSource = chatDataSource
    )

    @Singleton
    @Provides
    fun provideSendMessage(
        chatRepository: ChatRepository
    ): SendMessage = SendMessage(chatRepository)
}