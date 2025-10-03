package com.puadevs.leetcoach.chat.di

import com.google.gson.GsonBuilder
import com.puadevs.leetcoach.chat.Constants.AI_BASE_URL
import com.puadevs.leetcoach.chat.datasource.ChatDataSourceImpl
import com.puadevs.leetcoach.chat.datasource.remote.LLMApi
import com.puadevs.leetcoach.chat.domain.ChatRepository
import com.puadevs.leetcoach.chat.domain.usecases.SendMessage
import com.puadevs.leetcoach.chat.repository.ChatDataSource
import com.puadevs.leetcoach.chat.repository.ChatRepositoryImpl
import com.puadevs.leetcoach.di.ChatRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChatModule {

    @Singleton
    @Provides
    @ChatRetrofit
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(AI_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    @Singleton
    @Provides
    fun provideLLMAPI(@ChatRetrofit retrofit: Retrofit): LLMApi =
        retrofit.create(LLMApi::class.java)

    @Singleton
    @Provides
    fun provideChatDataSource(
        llmApi: LLMApi
    ): ChatDataSource = ChatDataSourceImpl(
        llmApiKey = "LLM_API_KEY",
        llmApi = llmApi
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