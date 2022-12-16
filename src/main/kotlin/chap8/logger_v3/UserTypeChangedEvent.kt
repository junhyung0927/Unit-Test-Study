package chap8.logger_v3

import chap8.UserType

data class UserTypeChangedEvent(
    val userId: Int,
    val oldType: UserType,
    val newType: UserType,
)