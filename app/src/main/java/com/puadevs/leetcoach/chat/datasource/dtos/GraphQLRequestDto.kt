package com.puadevs.leetcoach.chat.datasource.dtos

data class GraphQLRequestDto(
    val query: String,
    val variables: Map<String, Any> = emptyMap()
)
