package chap9.v1

import chap8.Company
import chap8.Company.Companion.DOMAIN_NAME
import chap8.Company.Companion.EMAIL_DELIMITER
import chap8.Company.Companion.EMAIL_SUFFIX
import chap8.UserType

class Database(
    connectionString: String
) {

    private val _connectionString: String = connectionString
    private var numberOfEmployees = 1

    /**
     * user 정보 가져오기
     */
    fun getUserById(userId: Int): List<Any> = listOf(
        userId,
        "new$EMAIL_DELIMITER$DOMAIN_NAME$EMAIL_SUFFIX",
        UserType.Customer,
        false
    )

    /**
     * user 정보 저장
     */
    fun saveUser(user: User) {
        println("save $user")
    }

    /**
     * company 정보 가져오기
     */
    fun getCompany(): List<Any> = listOf(DOMAIN_NAME, numberOfEmployees)

    /**
     * company 정보 저장
     */
    fun saveCompany(company: Company) {
        println("save $company")
    }

}