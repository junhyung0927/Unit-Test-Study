package chap5.detailsleaked

class AfterUser {

    private var _name: String = ""
    val name: String = _name

    fun setName(name: String) {
        _name = normalizeName(name)
    }

    private fun normalizeName(name: String): String {

        val result: String = name ?: "".trim()

        if (result.length > 50) {
            return result.substring(0, 50)
        }

        return result
    }
}