package chap6.refactoring.mocks

import java.io.File
import java.io.FileWriter

class IFileSystemImpl : IFileSystem {

    override fun getFiles(directoryName: String): List<String>? = File(directoryName).list()?.toList()?.sorted()

    override fun writeAllText(filePath: String, content: String) {
        FileWriter(filePath).use { it.write(content) }
    }

    override fun readAllLines(filePath: String): MutableList<String> {

        val linesList = mutableListOf<String>()
        val bufferedReader = File(filePath).bufferedReader()
        bufferedReader.useLines { line ->
            line.forEach {
                linesList.add(it)
            }
        }

        return linesList
    }
}