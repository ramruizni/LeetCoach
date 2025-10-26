package com.puadevs.leetcoach.chat.datasource.dtos

data class ProblemListDto(
    val stat_status_pairs: List<ProblemPairDto>
)

data class ProblemPairDto(
    val stat: ProblemStatDto
)

data class ProblemStatDto(
    val question_id: Int,
    val frontend_question_id: Int,
    val question__title_slug: String
)
