package chap9.v2

class Bus: IBus {
    override fun send(message: String) {
        println(message)
    }
}