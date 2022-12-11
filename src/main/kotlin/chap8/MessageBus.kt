package chap8

class MessageBus : IMessageBus {

    private val bus = Bus()
    override fun sendEmailChangedMessage(userId: Int, newEmail: String) {
        bus.send("Subject: USER; Type: EMAIL CHANGED; Id: ${userId}; NewEmail: $newEmail")
    }
}