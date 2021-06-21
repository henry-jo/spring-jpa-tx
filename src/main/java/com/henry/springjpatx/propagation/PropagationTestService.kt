package com.henry.springjpatx.propagation

import com.henry.springjpatx.dto.FruitInfo
import com.henry.springjpatx.repo.FruitInfoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronizationManager

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

        // REQUIRES_NEW 트랜잭션을 열고 그 안에서 값을 변경해도 dirty check가 작동한다.
        println("parent tx end !")
    }

    fun propagationTest4() {
        val fruit = fruitInfoRepository.findById(4).orElse(null)
        propagationTestService2.childRequiresNewTxSave(fruit)

        println("parent tx end !")
    }

    @Transactional(readOnly = true)
    fun propagationTest5() {
        println(TransactionSynchronizationManager.isCurrentTransactionReadOnly())
        // 자식 tx에서 readOnly=true여도 부모 속성값을 따라감. (기본 propagation이 PROPAGATION_REQUIRED 이므로)
        println("tx end !")
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    fun propagationTest6() {
        println(TransactionSynchronizationManager.isCurrentTransactionReadOnly())
        // 부모 tx의 readOnly값이 false여도 자식 tx propagation이 REQUIRES_NEW라면 readonly=true가 된다.
        println("tx end !")
    }
}
