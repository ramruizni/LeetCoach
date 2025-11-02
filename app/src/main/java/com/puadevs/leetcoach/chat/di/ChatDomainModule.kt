package com.puadevs.leetcoach.chat.di

import com.puadevs.leetcoach.chat.domain.ChatRepository
import com.puadevs.leetcoach.chat.domain.usecases.ObserveMessages
import com.puadevs.leetcoach.chat.domain.usecases.SendMessage
import com.puadevs.leetcoach.chat.domain.usecases.StartNewChat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChatDomainModule {

    @Singleton
    @Provides
    fun provideStartNewChat(
        chatRepository: ChatRepository
    ): StartNewChat = StartNewChat(chatRepository)

    @Singleton
    @Provides
    fun provideObserveMessages(
        chatRepository: ChatRepository
    ): ObserveMessages = ObserveMessages(chatRepository)

    @Singleton
    @Provides
    fun provideSendMessage(
        chatRepository: ChatRepository
    ): SendMessage = SendMessage(chatRepository)
}