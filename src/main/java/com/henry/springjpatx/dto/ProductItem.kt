package com.henry.springjpatx.dto

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "product_item")
@EntityListeners(ProductItemListener::class)
data class ProductItem (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(name = "amount")
    var amount: Int = 0,

    @Column(name = "name")
    var name: String = "",

    @Column(name = "made_by")
    var madeBy: String = "",

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()
)