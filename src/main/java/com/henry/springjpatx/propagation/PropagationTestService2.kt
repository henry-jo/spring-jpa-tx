package com.henry.springjpatx.propagation

import com.henry.springjpatx.dto.FruitInfo
import com.henry.springjpatx.repo.FruitInfoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

/**
 * Annotation Proxy 적용을 위해 만든 서비스
 */
@Service
class PropagationTestService2(
    private val fruitInfoRepository: FruitInfoRepository
) {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun childRequiresNewTxIncludeException() {
        println("child tx start !")
        fruitInfoRepository.save(FruitInfo(name = "apple"))

        throw RuntimeException()
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun childRequiresNewTx() {
        println("child tx start !")
        fruitInfoRepository.save(FruitInfo(name = "apple"))
    }
}