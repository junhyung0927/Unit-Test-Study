package chap8

interface IMessageBus {
    fun sendEmailChangedMessage(userId: Int, newEmail: String)

}