package com.puadevs.leetcoach.chat.datasource

import android.content.Context
import com.puadevs.leetcoach.chat.Constants.CHAT_LLM_MODEL
import com.puadevs.leetcoach.chat.datasource.converters.toDto
import com.puadevs.leetcoach.chat.datasource.dtos.ChatRequestDto
import com.puadevs.leetcoach.chat.datasource.dtos.GraphQLRequestDto
import com.puadevs.leetcoach.chat.datasource.remote.ChatApi
import com.puadevs.leetcoach.chat.datasource.remote.LeetCodeApi
import com.puadevs.leetcoach.chat.domain.models.Message
import com.puadevs.leetcoach.chat.domain.models.MessageRole
import com.puadevs.leetcoach.chat.repository.ChatDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking

class ChatDataSourceImpl(
    private val context: Context,
    private val api: ChatApi,
    private val leetCodeApi: LeetCodeApi,
    private val apiKey: String
) : ChatDataSource {

    private val _messages: MutableStateFlow<List<Message>> = MutableStateFlow(emptyList())

    private val problemMapping: Map<Int, String> by lazy {
        runBlocking {
            val response = leetCodeApi.getAllProblems()
            response.stat_status_pairs.associate {
                it.stat.frontend_question_id to it.stat.question__title_slug
            }
        }
    }

    private val systemPrompt: String by lazy {
        context.assets.open("prompts/leetcode_coach.md")
            .bufferedReader()
            .use { it.readText() }
    }

    private suspend fun getProblemDescription(number: Int): String? {
        val titleSlug = problemMapping[number]
            ?: return null

        val query = """
            query questionData(${'$'}titleSlug: String!) {
              question(titleSlug: ${'$'}titleSlug) {
                questionId
                title
                content
                difficulty
              }
            }
        """.trimIndent()

        val request = GraphQLRequestDto(
            query = query,
            variables = mapOf("titleSlug" to titleSlug)
        )

        val response = leetCodeApi.getQuestion(request)
        val question = response.data?.question

        return question?.let {
            """
            |# Problem $number: ${it.title}
            |**Difficulty**: ${it.difficulty}
            |
            |${it.content}
            """.trimMargin()
        }
    }

    override suspend fun startNewChat(problemNumber: Int): String? {
        _messages.value = emptyList()
        val description = getProblemDescription(problemNumber)
        if (description == null) {
            _messages.value = listOf(Message(MessageRole.ASSISTANT, "Problem not found"))
            return null
        }

        _messages.value = listOf(
            Message(MessageRole.SYSTEM, systemPrompt),
            Message(MessageRole.SYSTEM, description)
        )

        return description
    }

    override fun observeMessages(): Flow<List<Message>> {
        return _messages.asStateFlow()
    }

    override suspend fun sendMessage(text: String) {
        val updatedMessages = _messages.value + Message(MessageRole.USER, text)
        _messages.value = updatedMessages

        val request = ChatRequestDto(
            model = CHAT_LLM_MODEL,
            messages = updatedMessages.map(Message::toDto)
        )

        val content = runCatching {
            api.chat("Bearer $apiKey", request)
                .choices
                .firstOrNull()
                ?.message
                ?.content
                ?: "(No response)"
        }.getOrElse { e ->
            e.printStackTrace()
            "(Error: ${e.message})"
        }

        _messages.value = updatedMessages + Message(MessageRole.ASSISTANT, content)
    }

    companion object {
        const val TAG = "ChatDataSource"
    }
}