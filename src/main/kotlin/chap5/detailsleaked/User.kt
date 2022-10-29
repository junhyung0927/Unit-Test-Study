package chap5.detailsleaked

class User {

    var name: String = ""

    fun normalizeName(name: String): String {

        val result = name ?: "".trim()

        if (result.length > 50) {
            return result.substring(0, 50)
        }

        return result
    }

}