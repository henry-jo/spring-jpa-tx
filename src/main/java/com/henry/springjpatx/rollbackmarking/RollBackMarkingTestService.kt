package com.henry.springjpatx.rollbackmarking

import com.henry.springjpatx.dto.FruitInfo
import com.henry.springjpatx.repo.FruitInfoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RollBackMarkingTestService(
    private val rollBackMarkingTestService2: RollBackMarkingTestService2,
    private val fruitInfoRepository: FruitInfoRepository
) {

    fun test1() {
        println("parent tx start !")
        fruitInfoRepository.save(FruitInfo(name = "banana"))

        try {
            rollBackMarkingTestService2.childRequiredTxIncludeException()
        } catch (e: Exception) {
            println("child exception catch")
        }

        println("parent tx end !")

        // rollBackMarkingTestService2.childRequiredTxIncludeException()를 try-catch로 감싸주었지만,
        // 해당 트랜잭션에 이미 롤백 마킹이 찍혔기 때문에, test1 트랜잭션을 빠져나갈 때 롤백이 된다.
    }

    fun test2() {
        println("parent tx start !")
        fruitInfoRepository.save(FruitInfo(name = "banana"))

        try {
            rollBackMarkingTestService2.childRequiresNewTxIncludeException()
        } catch (e: Exception) {
            println("child exception catch")
        }

        println("parent tx end !")

        // test1과는 다르게 rollBackMarkingTestService2가 이미 존재하는 트랜잭션에 참여하는 것이 아닌 새로운 트랜잭션을 만들어 수행한다.
        // 따라서 새로운 트랜잭션의 exception을 try-catch로 감싸주었으므로 test2는 롤백되지 않고 정상적으로 커밋이 된다.
    }
}