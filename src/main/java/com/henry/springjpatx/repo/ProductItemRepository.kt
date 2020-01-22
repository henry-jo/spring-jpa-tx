package com.henry.springjpatx.repo

import com.henry.springjpatx.dto.ProductItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductItemRepository : JpaRepository<ProductItem, Long> {
    fun findAllByName(name: String): List<ProductItem>
}