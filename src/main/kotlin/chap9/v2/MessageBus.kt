package chap9.v2

class MessageBus(
    private val bus: IBus
) : IMessageBus {
    override fun sendEmailChangedMessage(userId: Int, newEmail: String) {
        bus.send("Subject: USER; Type: EMAIL CHANGED; Id: ${userId}; NewEmail: $newEmail")
    }
}