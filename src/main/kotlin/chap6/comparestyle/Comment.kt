package chap6.comparestyle

import java.time.LocalDate

data class Comment(
    val text: String,
    val author: String,
    val dateCreated: LocalDate
)
