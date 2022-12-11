package chap7

import chap7.step3.Company
import chap7.step3.Company.Companion.EMAIL_DELIMITER


data class User(
    var userId: Int,
    var email: String,
    var type: UserType,
    val isEmailConfirmed: Boolean,
) {

    private val _emailChangeEvents: MutableList<EmailChangeEvent> = mutableListOf()
    val emailChangeEvent: List<EmailChangeEvent> = _emailChangeEvents

    fun changeEmail(userId: Int, newEmail: String) {
        val data = Database.getUserById(userId)

        this.userId = data[0] as Int
        this.email = data[1] as String
        this.type = data[2] as UserType

        if (email == newEmail) return

        val companyData = Database.getCompany()
        val companyDomainName = companyData[0] as String
        val numberOfEmployees = companyData[1] as Int

        val emailDomain = newEmail.split(EMAIL_DELIMITER).last()

        val isEmailCorporate = emailDomain == companyDomainName
        val newType = if (isEmailCorporate) {
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
            val newNumber = numberOfEmployees + delta
            Database.saveCompany(newNumber)
        }

        this.email = newEmail
        this.type = newType

        Database.saveUser(this)
        MessageBus.sendEmailChangedMessage(userId, newEmail)
    }

    fun changeEmailStep1(newEmail: String, companyDomainName: String, numberOfEmployees: Int): Int {
        if (email == newEmail) {
            return numberOfEmployees
        }
        var newNumberOfEmployees = numberOfEmployees

        val emailDomain = newEmail.split(EMAIL_DELIMITER).last()
        val isEmailCorporate = emailDomain == companyDomainName
        val newType = if (isEmailCorporate) {
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
            val newNumber = newNumberOfEmployees + delta
            newNumberOfEmployees = newNumber
        }

        this.email = newEmail
        this.type = newType

        return newNumberOfEmployees
    }

    fun changeEmailStep3(newEmail: String, company: Company) {

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
        }

        email = newEmail
        type = newType

        _emailChangeEvents.add(EmailChangeEvent(userId, newEmail))
    }

    fun canChangeEmail() : String? {
        if (isEmailConfirmed) return "Can't change a confirmed email"

        return null
    }

}