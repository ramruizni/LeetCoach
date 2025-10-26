package com.puadevs.leetcoach.chat.datasource.converters

import com.puadevs.leetcoach.chat.datasource.dtos.MessageDto
import com.puadevs.leetcoach.chat.domain.models.Message

fun Message.toDto() = MessageDto(
    role = this.role.name.lowercase(),
    content = this.content
)