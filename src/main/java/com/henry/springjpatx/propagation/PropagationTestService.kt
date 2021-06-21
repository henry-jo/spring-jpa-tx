package com.henry.springjpatx.propagation

import com.henry.springjpatx.dto.FruitInfo
import com.henry.springjpatx.repo.FruitInfoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PropagationTestService(
    private val propagationTestService2: PropagationTestService2,
    private val fruitInfoRepository: FruitInfoRepository
) {

    fun propagationTest1() {
        println("parent tx start !")
        fruitInfoRepository.save(FruitInfo(name = "banana"))

        // throw exception in requires_new tx
        propagationTestService2.childRequiresNewTxIncludeException()

        // 부모 tx -> 자식 tx (exception 발생) : 부모 + 자식 둘 다 롤백
    }

    fun propagationTest2() {
        println("parent tx start !")
        fruitInfoRepository.save(FruitInfo(name = "banana"))

        propagationTestService2.childRequiresNewTx()

        throw RuntimeException()

        // 부모 tx -> 자식 tx -> 부모 tx (exception 발생) : 자식 tx는 커밋되고 부모만 롤백
    }

    fun propagationTest3() {
        val fruit = fruitInfoRepository.findById(4).orElse(null)
        propagationTestService2.childRequiresNewTxChange(fruit)

        println("parent tx end !")
    }

    fun propagationTest4() {
        val fruit = fruitInfoRepository.findById(4).orElse(null)
        propagationTestService2.childRequiresNewTxSave(fruit)

        println("parent tx end !")
    }
}