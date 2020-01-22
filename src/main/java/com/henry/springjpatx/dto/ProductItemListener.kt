package com.henry.springjpatx.dto

import javax.persistence.PostUpdate
import javax.persistence.PreUpdate

class ProductItemListener {
    @PreUpdate
    fun onPreUpdate(productItem: ProductItem) {
        println("productItem ID : ${productItem.id} preupdate")
    }

    @PostUpdate
    fun onPostUpdate(productItem: ProductItem) {
        println("productItem ID : ${productItem.id} postupdate")
    }
}