package chap6.comparestyle

import chap2.Product
import kotlin.math.min

class PriceEngine {

    fun calculateDiscount(product: List<Product>): Double {
        val discount: Double = product.count() * 0.01
        return min(discount, 0.2)
    }

}