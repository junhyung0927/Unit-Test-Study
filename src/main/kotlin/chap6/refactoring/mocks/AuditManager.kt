package chap6.refactoring.mocks

import java.time.LocalDate
import kotlin.io.path.Path

class AuditManager(
    maxEntriesPerFile: Int,
    directoryName: String,
    fileSystem: IFileSystemImpl
) {
    private val _maxEntriesPerFile: Int = maxEntriesPerFile
    private val _directoryName: String = directoryName
    private val _fileSystem: IFileSystemImpl = fileSystem

    fun addRecord(visitorName: String, timeOfVisit: LocalDate) {

        /**
         * IFileSystem 인터페이스를 활용하여 getFiles 구현체 호출
         */
        val filePaths = _fileSystem.getFiles(_directoryName)
        val sorted: List<Pair<Int, String>> = sortByIndex(filePaths!!)

        val newRecord = "$visitorName; $timeOfVisit"

        if (sorted.isEmpty()) {

            /**
             * IFileSystem 인터페이스를 활용하여 writeAllText 구현체 호출
             */
            _fileSystem.writeAllText("$_directoryName/audit_1.txt", newRecord)

        } else {

            val (currentFileIndex: Int, currentFilePath: String) = sorted.last()
            println(currentFilePath)
            /**
             * IFileSystem 인터페이스를 활용하여 readAllLines 구현체 호출
             */
            val linesList = _fileSystem.readAllLines("$_directoryName/$currentFilePath")

            if (linesList.count() < _maxEntriesPerFile) {
                linesList.add(newRecord)
                val newContent = linesList.joinToString("\r\n")
                /**
                 * IFileSystem 인터페이스를 활용하여 writeAllText 구현체 호출
                 */
                _fileSystem.writeAllText("$_directoryName/$currentFilePath", newContent)
            } else {
                val newIndex = currentFileIndex.plus(1)
                val newName = "audit_$newIndex.txt"
                /**
                 * IFileSystem 인터페이스를 활용하여 writeAllText 구현체 호출
                 */
                _fileSystem.writeAllText("$_directoryName/$newName", newRecord)
            }
        }
    }

    /**
     * 인덱스 별로 정렬
     */
    private fun sortByIndex(files: List<String>): List<Pair<Int, String>> {
        val afterFiles = mutableListOf<Pair<Int, String>>()
        val index = files.map {
            getIndex(it)
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

    /**
     * 인덱스 파싱하기
     */
    private fun getIndex(filePath: String): Int {
        val fileName = Path(filePath).toString()
        return fileName.split('_')[1].split('.')[0].toInt()
    }
}

fun main() {
    val file = AuditManager(3, "/Users/junhyung/fileExample", IFileSystemImpl())
    file.addRecord("jun", LocalDate.now())
}