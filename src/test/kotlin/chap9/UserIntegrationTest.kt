package chap9

import chap8.Company
import chap8.CompanyFactory
import chap8.UserType
import chap8.logger_v2.DomainLogger
import chap9.v1.*
import chap9.v1.Database
import chap9.v1.User
import chap9.v1.UserController
import chap9.v2.IBus
import chap9.v2.MessageBus
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import kotlin.test.assertEquals

class UserIntegrationTest {

    companion object {
        private const val CONNECTION_STRING = "TEST"
    }

    @Test
    fun changing_email_from_corporate_to_non_corporate_v1() {
        val db = Database(CONNECTION_STRING)
        val user: User = createUser("user@gmail.com", UserType.Employee, db)
        createCompany("gmail.com", 1, db)

        val mockMessageBus = mock<MessageBus>()
        val mockLogger = mock<DomainLogger>()
        val sut = UserController(
            db, mockMessageBus, mockLogger
        )

        val result = sut.changeEmail(user.userId, "new@naver.com")

        assertEquals("OK", result)

        val userData = db.getUserById(user.userId)
        val userFromDb = UserFactory.create(userData)
        assertEquals("new@naver.com", userFromDb.email)
        assertEquals(UserType.Customer, userFromDb.type)

        val companyData = db.getCompany()
        val companyFromDb = CompanyFactory.create(companyData)
        assertEquals(0, companyFromDb.numberOfEmployees)

        verify(mockMessageBus, times(1))
            .sendEmailChangedMessage(user.userId, "new@naver.com")

        verify(mockLogger, times(1))
            .userTypeHasChanged(user.userId, UserType.Employee, UserType.Customer)
    }

    @Test
    fun changing_email_from_corporate_to_non_corporate_v2() {

        val db = Database(CONNECTION_STRING)
        val user: User = createUser("user@gmail.com", UserType.Employee, db)
        createCompany("gmail.com", 1, db)

        val mockBus = mock<IBus>()
        val messageBus = MessageBus(mockBus)
        val mockLogger = mock<DomainLogger>()
        val sut = UserController(
            db, messageBus, mockLogger
        )
        // ...

        verify(mockBus, times(1))
            .send(
                "Type: USER EMAIL CHANGED;" +
                        "ID: ${user.userId};" +
                        "NewEmail: new@gmail.com"
            )

    }

    @Test
    fun changing_email_from_corporate_to_non_corporate_v3() {

        val db = Database(CONNECTION_STRING)
        val user: User = createUser("user@gmail.com", UserType.Employee, db)
        createCompany("gmail.com", 1, db)

        val spyBus = BusSpy()
        val messageBus = MessageBus(spyBus)
        val mockLogger = mock<DomainLogger>()
        val sut = UserController(
            db, messageBus, mockLogger
        )

        // ...

        spyBus.shouldSendNumberOfMessages(1)
            .withEmailChangedMessage(user.userId, "new@gmail.com")

    }

    private fun createCompany(domainName: String, numberOfEmployees: Int, dataBase: Database): Company {

        val company = Company(domainName, numberOfEmployees)
        dataBase.saveCompany(company)
        return company
    }

    private fun createUser(email: String, type: UserType, dataBase: Database): User {
        val user = User(
            userId = 0,
            email = email,
            type = type,
            isEmailConfirmed = false,
            domainEvents = mutableListOf()
        )
        dataBase.saveUser(user)
        return user
    }
}