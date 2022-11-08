package chap6.refactoring.functional

import java.time.LocalDate
import kotlin.io.path.Path

/**
 * AuditManager 클래스는 결정을 내리기 위해 파일 시스템에 대해 알아야 할 모든 것을 포함한다.
 */
class AuditManager(
    maxEntriesPerFile: Int
) {

    private val _maxEntriesPerFile: Int = maxEntriesPerFile

    fun addRecord(
        files: List<FileContent>?,
        visitorName: String,
        timeOfVisit: LocalDate
    ) : FileUpdate {

        val sorted: List<Pair<Int, FileContent>> = sortByIndex(files!!)

        val newRecord = "$visitorName; $timeOfVisit"

        if (sorted.isEmpty()) {
            /**
             * 작업 폴더의 파일을 직접 변경하지 않고, FileUpdate 데이터 클래스에 담아 수행하려는 부작용에 대한 명령을 반환한다.
             */
            return FileUpdate("audit_1.txt", newRecord)
        } else {
            val (currentFileIndex: Int, currentFile: FileContent) = sorted.last()
            val linesList = currentFile.lines

            return if (linesList.count() < _maxEntriesPerFile) {
                linesList.add(newRecord)
                val newContent = linesList.joinToString("\r\n")
                FileUpdate(currentFile.fileName, newContent)
            } else {
                val newIndex = currentFileIndex.plus(1)
                val newName = "audit_$newIndex.txt"
                FileUpdate(newName, newRecord)
            }
        }
    }

    private fun sortByIndex(files: List<FileContent>): List<Pair<Int, FileContent>> {
        val afterFiles = mutableListOf<Pair<Int, FileContent>>()
        val index = files.map {
            getIndex(it.fileName)
        }

        for (i in 0..files.size.minus(1)) {
            try {
                afterFiles.add(Pair(index[i], files[i]))
            } catch (e: Exception) {
                println(e)
            }
        }

        return afterFiles
    }

    private fun getIndex(filePath: String): Int {
        val fileName = Path(filePath).toString()
        return fileName.split('_')[1].split('.')[0].toInt()
    }
}