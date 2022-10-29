package chap6

import chap2.Product
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class OrderTest {

    @Test
    fun adding_a_product_to_an_order() {

        val product = Product.Shampoo
        val sut = Order()

        sut.addProduct(product)

        assertEquals(1, sut.product.count())
        assertEquals(product, sut.product[0])
    }

}