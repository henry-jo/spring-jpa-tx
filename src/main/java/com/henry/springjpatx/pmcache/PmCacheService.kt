package com.henry.springjpatx.pmcache

import com.henry.springjpatx.repo.ProductItemRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class PmCacheService (
    private val productItemRepository: ProductItemRepository
) {

    @Transactional
    fun getProductItemTest1() {
        val product1 = productItemRepository.findById(1L).orElse(null)
        println(product1)
        val product2 = productItemRepository.findById(1L).orElse(null)
        println(product2)
    }

    @Transactional
    fun getProductItemTest2(){
        val product1 = productItemRepository.findByName("apple")
        println(product1)
        val product2 = productItemRepository.findByName("apple")
        println(product2)
    }

    @Transactional
    fun getProductItemTest3_1() {
        val product = productItemRepository.findById(1L).orElse(null)
    }

    @Transactional
    fun getProductItemTest3_2() {
        val product = productItemRepository.findById(1L).orElse(null)
    }
}