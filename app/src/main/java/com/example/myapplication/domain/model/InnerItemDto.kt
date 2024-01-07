package com.example.myapplication.domain.model

import java.util.UUID

data class InnerItemDto(
    val number: Int,
    var prevNumber: Int? = null,
    val uuid: UUID = UUID.randomUUID()
)