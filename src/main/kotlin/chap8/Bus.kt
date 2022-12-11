package chap8

class Bus: IBus {
    override fun send(message: String) {
        println(message)
    }
}