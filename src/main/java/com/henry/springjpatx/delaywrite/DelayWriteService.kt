package com.henry.springjpatx.delaywrite

import com.henry.springjpatx.dto.ProductItem
import com.henry.springjpatx.repo.ProductItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DelayWriteService(
    private val productItemRepository: ProductItemRepository
) {

    fun test1() {
        productItemRepository.save(ProductItem(name = "banana", madeBy = "itner"))
        println("UPDATE 쿼리는 이미 날아갔는가?")
        val productItemList = productItemRepository.findAll()
        println("UPDATE 쿼리는 이미 날아갔는가?")
    }

    fun test2() {
        productItemRepository.save(ProductItem(name = "banana", madeBy = "itner"))
        val productItem = productItemRepository.findById(1L).orElse(null)
        println(productItem)
    }

    fun test3() {
        val productItem = productItemRepository.findById(1).orElse(null)
        productItem.amount = 10
        productItemRepository.save(productItem)
        println("UPDATE 쿼리는 이미 날아갔는가?")
    }

    fun test4() {
        val product1 = productItemRepository.findById(1L).orElse(null)
        val product2 = productItemRepository.findById(1L).orElse(null)
        println("----")
    }

    fun test5() {
        productItemRepository.save(ProductItem(name = "bananana", madeBy = "itner"))
        val product = productItemRepository.findAllByName("bananana")
        println(product)
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    fun test6() {
        val productItem = productItemRepository.findById(1L).orElse(null)
        productItem.amount = 1000

        println("update query not working")
        productItemRepository.save(productItem)

        val productItem1 = productItemRepository.findAllByName("bananana")
        println(productItem1)
    }

    fun test7() {
        val productItem = productItemRepository.findById(1L).orElse(null)
        productItem.amount = 10000

        println("update query not working")
        productItemRepository.save(productItem)

        val productItem1 = productItemRepository.findById(2L).orElse(null)
        println(productItem1)
    }
}