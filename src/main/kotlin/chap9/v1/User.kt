package chap9.v1

import chap8.Company
import chap8.UserType
import chap8.logger_v2.DomainLogger

data class User(
    val userId: Int,
    var email: String,
    var type: UserType,
    val isEmailConfirmed: Boolean,
    val domainEvents: MutableList<IDomainEvent>
) {

    fun canChangeEmail(): String? {
        if (isEmailConfirmed) return "Can't change a confirmed email"
        return null
    }

    fun changeEmail(newEmail: String, company: Company) {

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

    }

    private fun addDomainEvent(userTypeChangedEvent: IDomainEvent) {
        domainEvents.add(userTypeChangedEvent)
    }
}