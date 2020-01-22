package com.henry.springjpatx.dirtychecking

import com.henry.springjpatx.repo.ProductItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DirtyCheckingTxService(
    private val productItemRepository: ProductItemRepository
) {

    fun txCommit(): Boolean = true

    fun getByName(name: String) = productItemRepository.findAllByName(name)

    @Transactional(readOnly = true)
    fun getByNameReadOnly(name: String) = productItemRepository.findAllByName(name)
}