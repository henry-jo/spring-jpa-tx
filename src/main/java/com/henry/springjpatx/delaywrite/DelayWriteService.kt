package com.henry.springjpatx.delaywrite

import com.henry.springjpatx.dto.ProductItem
import com.henry.springjpatx.repo.FruitInfoRepository
import com.henry.springjpatx.repo.ProductItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DelayWriteService(
    private val productItemRepository: ProductItemRepository,
    private val fruitInfoRepository: FruitInfoRepository
) {

    fun test1() {
        productItemRepository.save(ProductItem(name = "banana", madeBy = "itner"))
        // JPA 식별자 전략이 IDENTITY일 경우에 DB에 SAVE를 해야 식별자를 알 수 있다.
        // 그래서 IDENTITY일 경우 save를 하는 순간, 바로 INSERT쿼리를 날리고 식별자를 취득해 영속성 컨텍스트에서 관리한다.
        val productItemList = productItemRepository.findAll()
        println("UPDATE 쿼리는 이미 날아갔는가?")
    }

    fun test2() {
        val productItem = productItemRepository.findById(1L).orElse(null)
        productItem.amount = productItem.amount + 1
        productItemRepository.save(productItem)
        // UPDATE쿼리는 이 타이밍에 날아가지 않는다. JPA는 기본적으로 쓰기지연을 지원한다.
        // 따라서, 이 경우에는 test2() 트랜잭션이 커밋될 때 DB에 쿼리가 날아간다.
        println("UPDATE 쿼리는 이미 날아갔는가?")
    }

    fun test3() {
        val productItem = productItemRepository.findById(1L).orElse(null)
        productItem.amount = productItem.amount + 1
        productItemRepository.save(productItem)
        println("------")
        productItemRepository.findAll()
        // 37 line UPDATE쿼리는 39 line이 실행되기 직전 auto-flush가 일어날 때 실행된다.
        // 즉, 37 line UPDATE쿼리 -> 39 line SELECT 쿼리 순으로 실행된다.
        println("------")
    }

    fun test4() {
        val productItem = productItemRepository.findById(1L).orElse(null)
        productItem.amount = productItem.amount + 1
        productItemRepository.save(productItem)
        println("------")
        fruitInfoRepository.findAll()
        // 48 line UPDATE쿼리는 50 line이 실행되기 직전 auto-flush가 일어날 때 실행되지 않는다.
        // select하는 테이블과 update 쓰기지연에 있는 테이블은 다르기 때문에 db에 전달하지 않는 것이다.
        // 즉, 50 line SELECT -> test4() 트랜잭션 커밋 -> 48 line UPDATE 쿼리 순으로 실행된다.
        println("------")
    }

    fun test5() {
        val productItem = productItemRepository.findById(1L).orElse(null)
        productItem.amount = productItem.amount + 1

        productItemRepository.save(productItem)
        println("------")
        productItemRepository.findAllByName("bananana")
        // Name 필드는 식별자 필드가 아니다. 즉 findAll()를 실행했을 때와 똑같은 실행 순서이다.
        // 같은 테이블을 조회하는 예제이므로 test3()와 실행 순서가 같다.
        println("------")
    }

    fun test6() {
        val productItem = productItemRepository.findById(1L).orElse(null)
        productItem.amount = productItem.amount + 1

        productItemRepository.save(productItem)
        println("------")
        val productItem1 = productItemRepository.findById(2L).orElse(null)
        // 여기서는 쓰기지연에 있는 같은 테이블을 조회하고 있지만, ID 식별자로 쿼리를 하고 있다.
        // 이런 경우 JPA는 식별자를 가지고 있으므로 쓰기지연에 들고 있는 UPDATE쿼리와 전혀 연관이 없다는 쿼리라는 사실을 알고 있다.
        // 그러므로 여기서는 75 line에서 일어나는 auto-flush에서 쓰기지연 쿼리가 DB에 전달되지 않는다.
        // 즉, 실행 순서는 75 line SELECT -> test6() 트랜잭션 커밋 -> 73 line UPDATE 쿼리
        println("------")
    }
}