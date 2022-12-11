package chap8

class UserFactory {

    companion object {
        fun create(data: List<Any>): User {
            require(data.size >= 4)

            return data.run {
                User(
                    userId = get(0) as Int,
                    email = get(1) as String,
                    type = get(2) as UserType,
                    isEmailConfirmed = get(3) as Boolean
                )
            }
        }
    }

}