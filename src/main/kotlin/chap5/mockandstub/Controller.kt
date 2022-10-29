package chap5.mockandstub

class Controller {
    private var _emailGateway: IEmailGateway? = null
    private var _database: IDatabase? = null

    constructor(emailGateway: IEmailGateway) {
        this._emailGateway = emailGateway
        this._database = null
    }

    constructor(database: IDatabase) {
        this._database = database
        this._emailGateway = null
    }

    fun greetUser(userEmail: String) {
        _emailGateway?.sendGreetingEmail(userEmail)
    }

    fun createReport(): Report {
        val numberOfUsers = _database!!.getNumberOfUsers()
        return Report(numberOfUsers)
    }

}