package com.henry.springjpatx.dirtychecking

import com.henry.springjpatx.repo.FruitInfoRepository
import com.henry.springjpatx.repo.ProductItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DirtyCheckingService(
    private val dirtyCheckingTxService: DirtyCheckingTxService,
    private val productItemRepository: ProductItemRepository,
    private val fruitInfoRepository: FruitInfoRepository
) {

    fun test1() {
        val product1 = productItemRepository.findById(1L).orElse(null)
        product1.amount = product1.amount + 1 // 엔티티 값 변경
        // osiv가 활성화되어있다면 여기서 product1은 영속성컨텍스트에 계속 살아있게된다. (영속 상태)
        // osiv가 반대로 비활성화 되어있다면 여기서 product1은 영속성컨텍스트에 남아있지 않는다. (준영속 detach 상태)
        dirtyCheckingTxService.txCommit() // 트랜잭션을 만들어 커밋
        // 커밋을 이용하여 flush를 하면 jpa는 dirty checking(변경감지)를 수행하고 최초의 엔티티 스냅샷과 다르다면 update쿼리를 날리게된다.

        println("product1은 변경감지가 되었는가?")
    }

    fun test2() {
        val product1 = productItemRepository.findById(1L).orElse(null)
        product1.amount = product1.amount + 1 // 엔티티 값 변경
        // osiv가 활성화되어있다면 여기서 product1은 영속성컨텍스트에 계속 살아있게된다. (영속 상태)
        // osiv가 반대로 비활성화 되어있다면 여기서 product1은 영속성컨텍스트에 남아있지 않는다. (준영속 detach 상태)
        productItemRepository.findAllByName("banana") // 바로 db select 쿼리를 날려서 auto-flush

        println("product1은 변경감지가 되었는가?")
    }

    fun test3() {
        val product1 = productItemRepository.findById(1L).orElse(null)
        product1.amount = product1.amount + 1 // 엔티티 값 변경
        // osiv가 활성화되어있다면 여기서 product1은 영속성컨텍스트에 계속 살아있게된다. (영속 상태)
        // osiv가 반대로 비활성화 되어있다면 여기서 product1은 영속성컨텍스트에 남아있지 않는다. (준영속 detach 상태)
        dirtyCheckingTxService.getByNameReadOnly("banana") // 바로 db select (tx readonly)쿼리를 날려서 auto-flush
        // 하지만 tx가 readonly라면 dirty checking은 수행하지 않는다.

        println("product1은 변경감지가 되었는가?")
    }

    @Transactional
    fun test4() {
        val product1 = productItemRepository.findById(1L).orElse(null)
        product1.amount = product1.amount + 1 // 엔티티 값 변경
        // osiv가 활성화되어있다면 여기서 product1은 영속성컨텍스트에 계속 살아있게된다. (영속 상태)
        // osiv가 반대로 비활성화 되어있다면 여기서 product1은 영속성컨텍스트에 남아있지 않는다. (준영속 detach 상태)
        productItemRepository.save(product1)
        dirtyCheckingTxService.getByNameReadOnly("banana") // 바로 db select 쿼리를 날려서 auto-flush
        // tx가 readonly라도 auto-flush는 수행하므로 49 line 쿼리가 수행된다.

        println("product1은 변경감지가 되었는가?")
    }

    @Transactional
    fun test4_1() {
        val fruit = fruitInfoRepository.findById(1L).orElse(null)
        fruit.name = fruit.name + "1" // 엔티티 값 변경
        // osiv가 활성화되어있다면 여기서 product1은 영속성컨텍스트에 계속 살아있게된다. (영속 상태)
        // osiv가 반대로 비활성화 되어있다면 여기서 product1은 영속성컨텍스트에 남아있지 않는다. (준영속 detach 상태)
        fruitInfoRepository.save(fruit)
        dirtyCheckingTxService.getByNameReadOnly("banana") // 바로 db select 쿼리를 날려서 auto-flush
        // tx가 readonly라도 auto-flush는 수행하지만, 다른 테이블을 조회하는 것이므로 여기서 49 line 쿼리가 수행되지 않는다.

        println("product1은 변경감지가 되었는가?")
    }

    fun test5() {
        val product1 = productItemRepository.findById(1L).orElse(null)
        product1.amount = product1.amount + 1 // 엔티티 값 변경
        // osiv가 활성화되어있다면 여기서 product1은 영속성컨텍스트에 계속 살아있게된다. (영속 상태)
        // osiv가 반대로 비활성화 되어있다면 여기서 product1은 영속성컨텍스트에 남아있지 않는다. (준영속 detach 상태)
        dirtyCheckingTxService.getByName("banana") // 바로 db select 쿼리를 날려서 auto-flush
        // tx가 readonly가 아니라면 dirty checking 수행
        // test1()과의 차이점은 test1()은 트랜잭션 commit으로 인한 flush로 dirty checking이 수행된다면,
        // test5()은 select쿼리로 발생하는 auto-flush로 dirty checking이 수행된다.

        println("product1은 변경감지가 되었는가?")
    }

    fun test6() {
        val product1 = productItemRepository.findById(1L).orElse(null)
        product1.amount = product1.amount + 1 // 엔티티 값 변경
        // osiv가 활성화되어있다면 여기서 product1은 영속성컨텍스트에 계속 살아있게된다. (영속 상태)
        // osiv가 반대로 비활성화 되어있다면 여기서 product1은 영속성컨텍스트에 남아있지 않는다. (준영속 detach 상태)
        dirtyCheckingTxService.getFruitInfoByName("banana") // 바로 db select 쿼리를 날려서 auto-flush
        // tx가 readonly가 아니라면 dirty checking 수행
        // 하지만, test5()와 차이가 있음.
        // test5()는 select 쿼리로 발생하는 auto-flush로 dirty checking이 수행되어 preupdate -> postupdate가 실행된다.
        // 하지만 test6()은 다른 테이블을 쿼리해서 그런지 몰라도 select 쿼리로 발생하는 auto-flush로 dirty checking을 수행하지만 실제로 쿼리를 날리지 않는다.
        // 그러므로 auto-flush때 preupdate만 불리고 실제로 쿼리를 날리지 않으므로 postupdate는 실행하지 않는다.

        // 결과적으로 test6() line 75의 실행순서는 다음과 같다.
        // select 쿼리 전 auto-flush 수행 -> dirty checking -> preupdate -> 실제 쿼리는 날라가지 않음 -> fruit info query -> commit -> flush -> dirty checking -> preupdate -> postupdate

        println("product1은 변경감지가 되었는가?")
    }
}