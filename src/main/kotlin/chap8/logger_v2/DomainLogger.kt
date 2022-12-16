package chap8.logger_v2

import chap8.UserType

class DomainLogger: IDomainLogger {

    private val logger = Logger()
    override fun userTypeHasChanged(userId: Int, oldType: UserType, newType: UserType) {
        logger.info(
            "User $userId changed type from $oldType to $newType"
        )
    }
}