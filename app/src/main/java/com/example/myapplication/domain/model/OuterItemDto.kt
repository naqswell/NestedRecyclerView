package com.example.myapplication.domain.model

import java.util.UUID

data class OuterItemDto (
    val innerItems: List<InnerItemDto>,
    val uuid: UUID = UUID.randomUUID(),
)