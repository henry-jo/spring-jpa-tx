package com.henry.springjpatx.dto

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "product_item")
data class ProductItem (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(name = "amount")
    val amount: Int = 0,

    @Column(name = "name")
    val name: String = "",

    @Column(name = "made_by")
    val madeBy: String = "",

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()
)