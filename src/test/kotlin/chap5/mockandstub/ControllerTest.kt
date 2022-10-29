package chap5.mockandstub

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class ControllerTest {

    @Test
    @DisplayName("Mock 라이브러리에서 Mock 클래스를 사용해 목을 생성")
    fun sending_a_greetings_emil() {

        val mock: IEmailGateway = mock(IEmailGateway::class.java)
        val sut = Controller(mock)

        sut.greetUser("jun@emil.com")

        verify(mock, times(1)).sendGreetingEmail("jun@emil.com")
    }

    @Test
    @DisplayName("Mock 클래스를 사용해 스텁을 생성")
    fun creating_a_report() {

        val stub: IDatabase = mock(IDatabase::class.java)
        whenever(stub.getNumberOfUsers()).thenReturn(10)
        val sut = Controller(stub)

        val report = sut.createReport()

        assertEquals(10, report.numberOfUser)

        //exam 5.3
        verify(stub, times(1)).getNumberOfUsers()

    }

}