package chap7.step1

import chap7.Database
import chap7.MessageBus
import chap7.User
import chap7.UserType
import chap7.step2.UserFactory
import chap7.step3.Company
import chap7.step3.CompanyFactory

class UserController {

    private val _database = Database
    private val _messageBus = MessageBus

//    fun changeEmail(userId: Int, newEmail: String) {
//        val data = _database.getUserById(userId)
//        val email = data[1] as String
//        val type = data[2] as UserType
//
//        val user = User(userId, email, type)
//
//        val companyData = _database.getCompany()
//        val companyDomainName = companyData[0] as String
//        val numberOfEmployees = companyData[1] as Int
//
//        val newNumberOfEmployees = user.changeEmailStep1(
//            newEmail, companyDomainName, numberOfEmployees
//        )
//
//        _database.saveCompany(newNumberOfEmployees)
//        _database.saveUser(user)
//        _messageBus.sendEmailChangedMessage(userId, newEmail)
//    }

    fun changeEmailStep2(userId: Int, newEmail: String) {
        val userData = _database.getUserById(userId)

        val user = UserFactory.create(userData)

        val companyData = _database.getCompany()
        val companyDomainName = companyData[0] as String
        val numberOfEmployees = companyData[1] as Int

        val newNumberOfEmployees = user.changeEmailStep1(
            newEmail, companyDomainName, numberOfEmployees
        )

        _database.saveCompany(newNumberOfEmployees)
        _database.saveUser(user)
        _messageBus.sendEmailChangedMessage(userId, newEmail)
    }

    fun changeEmailStep3(userId: Int, newEmail: String): String {

        val userData = _database.getUserById(userId)
        val user = UserFactory.create(userData)

        val error = user.canChangeEmail()
        error?.let { return it }

        val companyData = _database.getCompany()
        val company = CompanyFactory.create(companyData)

        user.changeEmailStep3(newEmail, company)

        _database.saveCompany(company)
        _database.saveUser(user)

        user.emailChangeEvent.forEach {
            _messageBus.sendEmailChangedMessage(it.userId, it.newEmail)
        }

        return "OK"

    }



}