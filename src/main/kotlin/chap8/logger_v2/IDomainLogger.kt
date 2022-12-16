package chap8.logger_v2

import chap8.UserType

interface IDomainLogger {
    fun userTypeHasChanged(userId: Int, oldType: UserType, newType: UserType)
}