package chap6.refactoring.functional

import java.io.File
import java.io.FileWriter
import kotlin.io.path.Path

class Persister {

    /**
     * 해당 경로의 모든 파일을 읽고
     */
    fun readDirectory(directoryName: String): List<FileContent>? {

        return File(directoryName)
            .list()
            ?.toList()
            ?.sorted()
            ?.map {
                val linesList = mutableListOf<String>()

                File("/Users/junhyung/fileExample/$it")
                    .bufferedReader()
                    .useLines { line ->
                        line.forEach { content ->
                            linesList.add(content)
                        }
                    }

                FileContent(it, linesList)
            }
    }

    fun applyUpdate(directoryName: String, update: FileUpdate) {

        val newFile = FileWriter("$directoryName/${update.fileName}")

        newFile.use {
            it.write(update.newContent)
        }
    }
}