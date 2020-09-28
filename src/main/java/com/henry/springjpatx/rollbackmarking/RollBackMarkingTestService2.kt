package com.henry.springjpatx.rollbackmarking

import com.henry.springjpatx.dto.FruitInfo
import com.henry.springjpatx.repo.FruitInfoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class RollBackMarkingTestService2(
    private val fruitInfoRepository: FruitInfoRepository
) {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun childRequiresNewTxIncludeException() {
        println("child tx start !")
        fruitInfoRepository.save(FruitInfo(name = "apple"))

        throw RuntimeException()
    }

    @Transactional(propagation = Propagation.REQUIRED)
    fun childRequiredTxIncludeException() {
        println("child tx start !")
        fruitInfoRepository.save(FruitInfo(name = "apple"))

        throw RuntimeException()
    }
}