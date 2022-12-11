package chap8

data class EmailChangeEvent(
    val userId: Int,
    val newEmail: String,
)
