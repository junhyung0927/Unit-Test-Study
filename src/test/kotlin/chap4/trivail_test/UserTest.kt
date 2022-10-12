package chap4.trivail_test

import chap4.trivial_test.User
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class UserTest {

    @Test
    fun test() {
        val user = User()

        val userCopy = user.copy(name = "Jun Hyung")

        assertEquals("Jun Hyung", userCopy.name)
    }
}