package chap6.refactoring.functional

import java.time.LocalDate

class ApplicationService(
    directoryName: String,
    auditManager: AuditManager,
    persister: Persister
) {
    private val _directoryName = directoryName
    private val _auditManager = auditManager
    private val _persister = persister

    fun addRecord(visitorName: String, timeOfVisit: LocalDate) {

        val files: List<FileContent>? = _persister.readDirectory(_directoryName)

        val update: FileUpdate = _auditManager.addRecord(
            files, visitorName, timeOfVisit
        )

        _persister.applyUpdate(_directoryName, update)
    }
}

fun main() {
    val application = ApplicationService(
        "/Users/junhyung/fileExample",
        AuditManager(3),
        Persister()
    )

    application.addRecord("jun", LocalDate.now())
}