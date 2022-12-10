package chap7

import chap7.step3.Company
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class UserTest {

//    @Test
//    fun changing_email_from_non_corporate_to_corporate() {
//        val company = Company("mycorp.com", 1)
//        val sut = User(1, "user@gmail.com", UserType.Customer)
//
//        sut.changeEmailStep3("new@mycorp.com", company)
//
//        assertEquals(2, company.numberOfEmployees)
//        assertEquals("new@mycorp.com", sut.email)
//        assertEquals(UserType.Employee, sut.type)
//    }

    @Test
    fun changing_email_from_corporate_to_non_corporate() {

        val company = Company("naver.com", 1)
        val sut = User(1, "user@naver.com", UserType.Employee, false)

        sut.changeEmailStep3("new@gmail.com", company)

        assertEquals(0, company.numberOfEmployees)
        assertEquals("new@gmail.com", sut.email)
        assertThat(UserType.Customer, `is`(sut.type))
        assertEquals(
            listOf(EmailChangeEvent(1, "new@gmail.com")),
            sut.emailChangeEvent
        )

    }

}