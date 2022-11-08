package chap6.refactoring.functional

import java.io.File
import java.io.FileWriter
import kotlin.io.path.Path

class Persister {

    /**
     * 작업 폴더의 파일과 해당 내용의 모든 라인을 수집해서 FileContent 데이터 클래스에 담아 반환한다.
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

    /**
     * AuditManager 클래스에서 업데이트 명령에 대한 반환 값을 받아 파일 시스템에 접근하여 쓴다.
     */
    fun applyUpdate(directoryName: String, update: FileUpdate) {

        val newFile = FileWriter("$directoryName/${update.fileName}")

        newFile.use {
            it.write(update.newContent)
        }
    }
}