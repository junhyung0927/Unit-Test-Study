package chap7.step3

class CompanyFactory {

    companion object {
        fun create(data: List<Any>): Company {
            require(data.size >= 3)

            return data.run {
                Company(
                    domainName = get(0) as String,
                    numberOfEmployees = get(1) as Int
                )
            }
        }
    }
}