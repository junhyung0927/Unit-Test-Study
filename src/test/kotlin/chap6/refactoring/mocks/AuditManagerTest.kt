package chap6.refactoring.mocks

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDate

class AuditManagerTest {

    @Test
    @DisplayName("현재 파일의 항목 수가 세 개에 도달 했을 때, 감사 항목이 하나 있는 새 파일을 생성하는지에 대한 검증")
    fun a_new_file_is_created_when_the_current_file_overflows() {

        val fileSystemMock = mock(IFileSystemImpl::class.java)
        whenever(fileSystemMock.getFiles("/Users/junhyung/fileExample"))
            .thenReturn(
                listOf(
                    "audit_1.txt",
                    "audit_2.txt",
                )
            )
        whenever(fileSystemMock.readAllLines("/Users/junhyung/fileExample/audit_2.txt"))
            .thenReturn(
                mutableListOf(
                    "jun; 2022-11-07",
                    "jun; 2022-11-07",
                    "jun; 2022-11-07"
                )
            )
        val sut = AuditManager(3, "/Users/junhyung/fileExample", fileSystemMock)

        sut.addRecord("jun", LocalDate.now())

        verify(fileSystemMock).writeAllText(
            "/Users/junhyung/fileExample/audit_3.txt",
            "jun; 2022-11-07"
        )

    }
}