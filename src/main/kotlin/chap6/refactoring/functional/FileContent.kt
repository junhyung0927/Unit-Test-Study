package chap6.refactoring.functional

data class FileContent(
    val fileName: String,
    val lines: MutableList<String>
)
