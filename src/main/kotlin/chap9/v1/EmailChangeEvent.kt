package chap9.v1

data class EmailChangeEvent(
    val userId: Int,
    val newEmail: String,
) : IDomainEvent
