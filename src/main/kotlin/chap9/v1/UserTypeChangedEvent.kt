package chap9.v1

import chap8.UserType

data class UserTypeChangedEvent(
    val userId: Int,
    val oldType: UserType,
    val newType: UserType,
) : IDomainEvent