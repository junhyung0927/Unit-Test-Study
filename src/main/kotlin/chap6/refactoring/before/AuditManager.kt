package chap6.refactoring.before

import java.io.File
import java.io.FileWriter
import java.time.LocalDate
import kotlin.io.path.Path

class AuditManager(
    maxEntriesPerFile: Int,
    directionality: String,
) {
    private val _maxEntriesPerFile: Int = maxEntriesPerFile
    private val _directoryName: String = directionality

    fun addRecord(visitorName: String, timeOfVisit: LocalDate) {

        val filePaths = File(_directoryName).list()?.toList()?.sorted()
        val sorted: List<Pair<Int, String>> = sortByIndex(filePaths!!)

        val newRecord = "$visitorName; $timeOfVisit"

        if (sorted.isEmpty()) {

            /**
             * 파일이 하나도 없을 때 생성
             */
            val newFile = FileWriter("$_directoryName/audit_1.txt")

            newFile.use {
                it.write(newRecord)
            }

        } else {

            /**
             * 해당 폴더의 마지막 파일의 내용 라인 개수 구하기
             */
            val (currentFileIndex: Int, currentFilePath: String) = sorted.last()
            val linesList = mutableListOf<String>()
            val bufferedReader = File("$_directoryName/$currentFilePath").bufferedReader()
            bufferedReader.useLines { line ->
                line.forEach {
                    linesList.add(it)
                }
            }

            /**
             * 파일의 내용이 3줄 이하 일 때는 바로 아래에 내용 삽입
             * 3줄 이상일 때는 파일을 하나 추가 한 후 내용 삽입
             */
            if (linesList.count() < _maxEntriesPerFile) {
                linesList.add(newRecord)
                val newContent = linesList.joinToString("\r\n")
                val newFile = FileWriter("$_directoryName/$currentFilePath")
                newFile.use {
                    it.write(newContent)
                }
            } else {
                val newIndex = currentFileIndex.plus(1)
                val newName = "audit_$newIndex.txt"
                val newFile = FileWriter("$_directoryName/$newName")
                newFile.use {
                    it.write(newRecord)
                }
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
    val file = AuditManager(3, "/Users/junhyung/fileExample")
    file.addRecord("jun", LocalDate.now())
}