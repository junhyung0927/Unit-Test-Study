package chap6.comparestyle

import chap5.mockandstub.Controller
import chap5.mockandstub.IEmailGateway
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.kotlin.verify

class ControllerTest {

    @Test
    fun sending_a_greetings_email() {
        val emailGateWay = mock(IEmailGateway::class.java)
        val sut = Controller(emailGateWay)

        sut.greetUser("jun@email.com")

        verify(emailGateWay, times(1))

    }

}