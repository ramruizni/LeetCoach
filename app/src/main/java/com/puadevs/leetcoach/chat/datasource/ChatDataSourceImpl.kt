package com.puadevs.leetcoach.chat.datasource

import android.content.Context
import android.util.Log
import com.puadevs.leetcoach.chat.Constants.CHAT_LLM_MODEL
import com.puadevs.leetcoach.chat.datasource.remote.ChatApi
import com.puadevs.leetcoach.chat.datasource.remote.LeetCodeApi
import com.puadevs.leetcoach.chat.datasource.dtos.ChatRequestDto
import com.puadevs.leetcoach.chat.datasource.dtos.GraphQLRequestDto
import com.puadevs.leetcoach.chat.datasource.dtos.MessageDto
import com.puadevs.leetcoach.chat.domain.models.Message
import com.puadevs.leetcoach.chat.repository.ChatDataSource
import kotlinx.coroutines.runBlocking

class ChatDataSourceImpl(
    private val context: Context,
    private val api: ChatApi,
    private val leetCodeApi: LeetCodeApi,
    private val apiKey: String
) : ChatDataSource {

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

    override suspend fun getProblemDescription(number: Int): String? {
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

    override suspend fun sendMessage(userMessage: String): String? {
        return try {
            val request = ChatRequestDto(
                model = CHAT_LLM_MODEL,
                messages = listOf(
                    MessageDto("system", systemPrompt),
                    MessageDto("user", userMessage)
                )
            )
            val response = api.chat("Bearer $apiKey", request)

            response.choices.firstOrNull()?.message?.content.orEmpty()
                .ifBlank { "No answer received." }
        } catch (e: Exception) {
            Log.e(TAG, "Chat response failed: ${e.message}")
            null
        }
    }

    companion object {
        const val TAG = "ChatDataSource"
    }
}