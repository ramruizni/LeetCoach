package com.puadevs.leetcoach.chat.di

import com.puadevs.leetcoach.chat.domain.ChatRepository
import com.puadevs.leetcoach.chat.repository.ChatDataSource
import com.puadevs.leetcoach.chat.repository.ChatRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChatInfrastructureModule {

    @Singleton
    @Provides
    fun provideChatRepository(
        chatDataSource: ChatDataSource
    ): ChatRepository = ChatRepositoryImpl(
        chatDataSource = chatDataSource
    )
}