package chap5.detailsleaked

class AfterUserController {

    fun renameUser(userId: Int, newName: String) {

        val user: AfterUser = getUserFromDatabase(userId)
        user.setName(newName)
        saveUserToDatabase(user)

    }

    private fun saveUserToDatabase(user: AfterUser) { }

    private fun getUserFromDatabase(userId: Int): AfterUser = AfterUser()

}