package chap6.comparestyle

import chap2.Product

class Order {
    private val _product = mutableListOf<Product>()
    val product = _product

    fun addProduct(product: Product) {
        _product.add(product)
    }

}