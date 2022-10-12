package chap4.brittle_test

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class UserRepositoryTest {

    @Test
    @DisplayName("데이터베이스에서 사용자를 가져올 때 UserRepository 클래스가 올바른 SQL 문을 생성하는지 검증")
    fun getById_executes_correct_sql_code() {
        val userRepository = UserRepository()

        userRepository.getById(5)

        assertEquals("SELECT * FROM dbo.[User] WHERE UserUD = 5", userRepository.lastExecuteSqlStatement)
    }

}