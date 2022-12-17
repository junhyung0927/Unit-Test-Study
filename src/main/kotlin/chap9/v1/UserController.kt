package chap9.v1

import chap8.CompanyFactory

import chap8.logger_v2.IDomainLogger
import chap9.v2.MessageBus


data class UserController(
    private val _database: Database,
    private val _messageBus: MessageBus,
    private val _domainLogger: IDomainLogger,
) {

    fun changeEmail(userId: Int, newEmail: String): String {

        val userData = _database.getUserById(userId)
        val user = UserFactory.create(userData)

        val error = user.canChangeEmail()
        error?.let { return it }

        val companyData = _database.getCompany()
        val company = CompanyFactory.create(companyData)

        user.changeEmail(newEmail, company)

        _database.saveCompany(company)
        _database.saveUser(user)

        EventDispatcher().dispatch(user.domainEvents)

        return "OK"
    }

}