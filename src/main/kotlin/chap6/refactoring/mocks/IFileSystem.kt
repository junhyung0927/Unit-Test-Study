package chap6.refactoring.mocks

interface IFileSystem {

    fun getFiles(directoryName: String): List<String>?

    fun writeAllText(filePath: String, content: String)

    fun readAllLines(filePath: String): MutableList<String>

}