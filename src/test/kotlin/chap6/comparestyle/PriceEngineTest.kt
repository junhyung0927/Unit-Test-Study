package chap6.comparestyle

import chap2.Product
import chap6.comparestyle.PriceEngine
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PriceEngineTest {

    @Test
    fun discount_of_two_products() {

        val product = listOf(
            Product.Shampoo,
            Product.Book
        )
        val sut = PriceEngine()

        val discount = sut.calculateDiscount(product)

        assertEquals(0.02, discount)

    }
}