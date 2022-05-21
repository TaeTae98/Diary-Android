package com.taetae98.diary.domain.exception

import java.text.SimpleDateFormat

data class KnownIssueException(
    override val message: String = "Test ${SimpleDateFormat.getDateInstance().format(System.currentTimeMillis())}"
) : Exception(message)