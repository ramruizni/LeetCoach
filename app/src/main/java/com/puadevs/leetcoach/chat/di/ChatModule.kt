package com.puadevs.leetcoach.chat.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.puadevs.leetcoach.BuildConfig
import com.puadevs.leetcoach.chat.Constants.CHAT_API_BASE_URL
import com.puadevs.leetcoach.chat.datasource.ChatDataSourceImpl
import com.puadevs.leetcoach.chat.datasource.remote.ChatApi
import com.puadevs.leetcoach.chat.datasource.remote.LeetCodeApi
import com.puadevs.leetcoach.chat.domain.ChatRepository
import com.puadevs.leetcoach.chat.domain.usecases.SendMessage
import com.puadevs.leetcoach.chat.repository.ChatDataSource
import com.puadevs.leetcoach.chat.repository.ChatRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class LeetCodeRetrofit

    @Singleton
    @Provides
    @ChatRetrofit
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(CHAT_API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    @Singleton
    @Provides
    @LeetCodeRetrofit
    fun provideLeetCodeRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://leetcode.com/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    @Singleton
    @Provides
    fun provideChatApi(@ChatRetrofit retrofit: Retrofit): ChatApi =
        retrofit.create(ChatApi::class.java)

    @Singleton
    @Provides
    fun provideLeetCodeApi(@LeetCodeRetrofit retrofit: Retrofit): LeetCodeApi =
        retrofit.create(LeetCodeApi::class.java)

    @Singleton
    @Provides
    fun provideChatDataSource(
        @ApplicationContext appContext: Context,
        api: ChatApi,
        leetCodeApi: LeetCodeApi
    ): ChatDataSource = ChatDataSourceImpl(
        context = appContext,
        api = api,
        leetCodeApi = leetCodeApi,
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