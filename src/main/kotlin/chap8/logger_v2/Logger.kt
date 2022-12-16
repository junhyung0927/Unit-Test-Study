package chap8.logger_v2

class Logger : ILogger {
    override fun info(log: String) {
        println("log: $log")
    }
}