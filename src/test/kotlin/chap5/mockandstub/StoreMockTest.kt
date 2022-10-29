package chap5.mockandstub

import chap2.Customer
import chap2.Product
import chap2.Store
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertFalse

class StoreMockTest {

    /**
     * 목과 스텁을 함께 사용
     * 준비된 응답을 설정하고 SUT에서 수행한 호출을 검사한다.
     */

    @Test
    @DisplayName("목이자 스텁인 storeMock")
    fun purchase_fails_when_not_enough_inventory() {

        val storeMock = mock(Store::class.java)
        whenever(storeMock.hasEnoughInventory(Product.Shampoo, 5)).thenReturn(false)
        val sut = Customer()

        val success: Boolean = sut.purChase(storeMock, Product.Shampoo, 5)

        assertFalse(success)
        verify(storeMock, times(0)).removeInventory(Product.Shampoo, 5)

    }
}