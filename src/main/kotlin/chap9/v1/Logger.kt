package chap9.v1

class Logger : ILogger {
    override fun info(log: String) {
        println("log: $log")
    }
}