package chap8

data class User(
    val userId: Int,
    var email: String,
    var type: UserType,
    val isEmailConfirmed: Boolean
) {

    private val _emailChangeEvents: MutableList<EmailChangeEvent> = mutableListOf()
    val emailChangeEvent: List<EmailChangeEvent> = _emailChangeEvents

    fun canChangeEmail() : String? {
        if (isEmailConfirmed) return "Can't change a confirmed email"
        return null
    }

    fun changeEmail(newEmail: String, company: Company) {

        require(canChangeEmail() == null)

        if (email == newEmail) {
            return
        }

        val newType = if (company.isEmailCorporate(newEmail)) {
            UserType.Employee
        } else {
            UserType.Customer
        }

        if (type != newType) {
            val delta = if (newType == UserType.Employee) {
                1
            } else {
                -1
            }
            company.changeNumberOfEmployees(delta)
        }

        email = newEmail
        type = newType

        _emailChangeEvents.add(EmailChangeEvent(userId, newEmail))
    }

}