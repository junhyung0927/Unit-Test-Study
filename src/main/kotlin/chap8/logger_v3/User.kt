package chap8.logger_v3

import chap8.Company
import chap8.EmailChangeEvent
import chap8.UserType
import chap8.logger_v2.DomainLogger

data class User(
    val userId: Int,
    var email: String,
    var type: UserType,
    val isEmailConfirmed: Boolean,
    val logger: Logger,
    val domainLogger: DomainLogger,
    val domainEvents: List<EmailChangeEvent>
) {

    private val _emailChangeEvents: MutableList<EmailChangeEvent> = mutableListOf()
    val emailChangeEvent: List<EmailChangeEvent> = _emailChangeEvents

    fun canChangeEmail(): String? {
        if (isEmailConfirmed) return "Can't change a confirmed email"
        return null
    }

    fun changeEmail(newEmail: String, company: Company) {

        logger.info(
            "changing email for user $userId to $newEmail"
        )

        require(canChangeEmail() == null)

        if (email == newEmail) {
            return
        }

        val newType = if (company.isEmailCorporate(newEmail)) {
            UserType.Employee
        } else {
            UserType.Customer
        }

        if (type != newType) {
            val delta = if (newType == UserType.Employee) {
                1
            } else {
                -1
            }
            company.changeNumberOfEmployees(delta)
            addDomainEvent(UserTypeChangedEvent(userId, type, newType))
        }

        email = newEmail
        type = newType

        addDomainEvent(EmailChangeEvent(userId, newEmail))

        logger.info(
            "$email is changed for user $userId"
        )
    }

    private fun addDomainEvent(userTypeChangedEvent: EmailChangeEvent) {
        // TODO messageBus.sendEmailChangedMessage로 변환
    }

    private fun addDomainEvent(userTypeChangedEvent: UserTypeChangedEvent) {
        // TODO domainLogger.userTypeHasChanged로 변환
    }
}