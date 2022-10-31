package chap6.functional.before

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.lang.StringBuilder
import java.nio.file.Files
import java.time.LocalDate
import javax.swing.filechooser.FileNameExtensionFilter
import kotlin.io.path.Path

class AuditManager(
    maxEntriesPerFile: Int,
    directionality: String,
) {
    private val _maxEntriesPerFile: Int = maxEntriesPerFile
    private val _directionality: String = directionality

    fun addRecord(visitorName: String, timeOfVisit: LocalDate) {
        val filePaths = File(_directionality).list()?.toList()
        val sorted: List<Pair<Int, String>> = sortByIndex(filePaths!!)

        val newRecord: String = "$visitorName;$timeOfVisit"

        if (sorted.isEmpty()) {
            val newFile = FileWriter("$_directionality/audit_1.txt")
            val buffer = BufferedWriter(newFile)
            buffer.write(newRecord)
        }

        val (currentFileIndex: Int, currentFilePath: String) = sorted.last()
        val linesList = mutableListOf<String>()
        val bufferedReader = File("$_directionality/$currentFilePath").bufferedReader()
        bufferedReader.useLines { line ->
            line.forEach { linesList.add(it) }
        }

        if (linesList.count() < _maxEntriesPerFile) {
            linesList.add(newRecord)
            val newContent: String = linesList.joinToString("\r\n")
            val newFile = FileWriter("$_directionality/$currentFilePath")
            val buffer = BufferedWriter(newFile)
            buffer.write(newContent)
        } else {
            val newIndex = currentFileIndex.plus(1)
            val newName = "audit_$newIndex.txt"
            val newFile = FileWriter("$_directionality/$newName")
            val buffer = BufferedWriter(newFile)
            buffer.write(newRecord)
        }
    }

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

    private fun getIndex(filePath: String): Int {
        val fileName = Path(filePath).toString()
        return fileName.split('_')[1].split('.')[0].toInt()
    }
}

fun main() {
    val file = AuditManager(3, "/Users/junhyung/test")
    file.addRecord("jun:", LocalDate.now())
}