package chap4.brittle_test

import chap4.trivial_test.User

class UserRepository {

    var lastExecuteSqlStatement: String = ""
        get() = field.ifEmpty() {
            throw IllegalStateException("실행할 구문이 없습니다.")
        }
        private set


    fun getById(id: Int): User {
        lastExecuteSqlStatement = "SELECT * FROM dbo.[User] WHERE UserUD = $id"
        return User("Jun Hyung")
    }
}