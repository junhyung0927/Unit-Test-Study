package chap9.v1

import chap8.EmailChangeEvent
import chap8.UserType
import chap8.logger_v2.DomainLogger

class UserFactory {

    companion object {
        fun create(data: List<Any>): User {
            require(data.size >= 4)

            return data.run {
                User(
                    userId = get(0) as Int,
                    email = get(1) as String,
                    type = get(2) as UserType,
                    isEmailConfirmed = get(3) as Boolean,
                    domainEvents = get(4) as MutableList<IDomainEvent>
                )
            }
        }
    }

}