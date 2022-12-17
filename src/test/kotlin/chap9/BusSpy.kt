package chap9

import chap9.v2.IBus
import kotlin.test.assertContains
import kotlin.test.assertEquals

class BusSpy: IBus {
    private val _sentMessages: MutableList<String> = mutableListOf()
    override fun send(message: String) {
        _sentMessages.add(message)
    }

    fun shouldSendNumberOfMessages(number: Int): BusSpy {
        assertEquals(number, _sentMessages.count())
        return this
    }

    fun withEmailChangedMessage(userId: Int, newEmail: String): BusSpy {
        val message = "Type: USER EMAIL CHANGE; " +
                "ID: $userId;" +
                "NewEmail: $newEmail"
        assertContains(_sentMessages, message)

        return this
    }

}