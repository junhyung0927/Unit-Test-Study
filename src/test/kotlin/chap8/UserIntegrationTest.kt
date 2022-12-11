package chap8

import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.kotlin.verify
import kotlin.test.assertEquals

class UserIntegrationTest {

    companion object {
        private const val CONNECTION_STRING = "TEST"
    }

    @Test
    fun changing_email_from_corporate_to_non_corporate() {

        val db = Database(CONNECTION_STRING)
        val user: User = createUser(
            email = "user@naver.com",
            type = UserType.Employee,
            dataBase = db
        )
        createCompany("TR", 1, db)

        val messageBusMock = mock(MessageBus::class.java)
        val sut = UserController(db, messageBusMock)

        //Act
        /**
         * DB를 직접 만들지 않아, database의 getByUserId 메서드를 살펴보면 정적으로 고정값을 가져와 새로운 이메일을 추가하더라도
         * 정적으로 고정된 값을 조회할 수 밖에 없음
         */
        val result = sut.changeEmail(userId = user.userId, newEmail = "new@TR.com")

        //Assert
        assertEquals("OK", result)

        val userData: List<Any> = db.getUserById(user.userId)
        val userFromDb: User = UserFactory.create(userData)

        assertEquals("new@TR.com", userFromDb.email)
        assertEquals(UserType.Customer, userFromDb.type)

        val companyData: List<Any> = db.getCompany()
        val companyFromDb = CompanyFactory.create(companyData)
        assertEquals(1, companyFromDb.numberOfEmployees)

        /**
         * 위의 이유로, User 데이터 클래스의 changEmail에서 새로운 Email과 기존의 Email의 조건문에서 걸려 return하여
         * sendEmailChangedMessage가 호출되지 않음.
         */
        verify(messageBusMock, times(0))
            .sendEmailChangedMessage(user.userId, "new@TR.com")
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
            isEmailConfirmed = false
        )
        dataBase.saveUser(user)
        return user
    }

}