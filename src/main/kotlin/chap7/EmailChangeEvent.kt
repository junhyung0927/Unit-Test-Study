package chap7

data class EmailChangeEvent(
    val userId: Int,
    val newEmail: String,
)
