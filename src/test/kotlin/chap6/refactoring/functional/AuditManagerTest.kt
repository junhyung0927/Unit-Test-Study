package chap6.refactoring.functional

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertEquals

class AuditManagerTest {

    @Test
    @DisplayName("목 없이 함수형 아키텍처를 사용한 테스트")
    fun a_new_file_is_created_when_the_current_file_overflows() {

        val sut = AuditManager(3)
        val files = listOf(
            FileContent("audit_1.txt", mutableListOf()),
            FileContent(
                "audit_2.txt", mutableListOf(
                    "jun; 2022-11-07",
                    "jun; 2022-11-07",
                    "jun; 2022-11-07",
                )
            ),
        )

        val update = sut.addRecord(files, "jun", LocalDate.now())

        assertEquals("audit_3.txt", update.fileName)
        assertEquals("jun; 2022-11-07", update.newContent)
    }
}