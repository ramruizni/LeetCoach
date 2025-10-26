package com.puadevs.leetcoach.chat.datasource.dtos

data class LeetCodeResponseDto(
    val data: QuestionDataDto?
)

data class QuestionDataDto(
    val question: QuestionDto?
)

data class QuestionDto(
    val questionId: String,
    val title: String,
    val content: String,
    val difficulty: String
)
