package chap5.detailsleaked

class UserController {

    fun renameUser(userId: Int, newName: String) {

        val user: User = getUserFromDatabase(userId)

        val normalizedName: String = user.normalizeName(newName)
        user.name = normalizedName

        saveUserToDatabase(user)

    }

    private fun saveUserToDatabase(user: User) { }

    private fun getUserFromDatabase(userId: Int): User = User()

}