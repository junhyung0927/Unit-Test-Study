package chap9.v2

interface IMessageBus {
    fun sendEmailChangedMessage(userId: Int, newEmail: String)

}