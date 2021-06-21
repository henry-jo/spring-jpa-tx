package com.henry.springjpatx.txsyncmanager

import com.henry.springjpatx.dto.FruitInfo
import com.henry.springjpatx.repo.FruitInfoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronization
import org.springframework.transaction.support.TransactionSynchronizationAdapter
import org.springframework.transaction.support.TransactionSynchronizationManager

@Service
@Transactional
class TxSyncManagerService(
    private val fruitInfoRepository: FruitInfoRepository
) {

    fun test1() {
        println("tx start !")

        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(
                object : TransactionSynchronizationAdapter() {
                    override fun afterCompletion(status: Int) {
                        if (status == TransactionSynchronization.STATUS_ROLLED_BACK) {
                            println("after rollback")
                        }
                    }
                })
        }

        fruitInfoRepository.save(FruitInfo(name = "bananana"))

        throw RuntimeException()
    }

    fun test2() {
        println("tx start !")

        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(
                object : TransactionSynchronizationAdapter() {
                    override fun afterCompletion(status: Int) {
                        if (status == TransactionSynchronization.STATUS_ROLLED_BACK) {
                            println("after rollback")
                            throw RuntimeException()
                        }
                    }
                })
        }

        fruitInfoRepository.save(FruitInfo(name = "bananana"))

        throw RuntimeException()
    }
}