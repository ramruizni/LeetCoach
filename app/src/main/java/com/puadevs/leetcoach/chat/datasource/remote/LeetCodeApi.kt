package com.puadevs.leetcoach.chat.datasource.remote

import com.puadevs.leetcoach.chat.datasource.dtos.GraphQLRequestDto
import com.puadevs.leetcoach.chat.datasource.dtos.LeetCodeResponseDto
import com.puadevs.leetcoach.chat.datasource.dtos.ProblemListDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LeetCodeApi {
    @POST("graphql")
    suspend fun getQuestion(
        @Body request: GraphQLRequestDto
    ): LeetCodeResponseDto

    @GET("api/problems/algorithms/")
    suspend fun getAllProblems(): ProblemListDto
}
