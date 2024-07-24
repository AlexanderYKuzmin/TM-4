package com.kuzmin.tm_4.common.util

import java.util.UUID

object Generator {
    fun generateUuid() = UUID.randomUUID().toString()
}