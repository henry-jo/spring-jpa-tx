package com.henry.springjpatx.pmcache

import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.Test

@RunWith(SpringRunner::class)
@SpringBootTest
class PmCacheServiceTest {

    @Autowired
    private lateinit var pmCacheService: PmCacheService

    /**
     * 식별자로 조회했을 경우 영속성 컨텍스트에 이미 엔티티가 존재하면 1차 캐시
     */
    @Test
    fun test1() {
        pmCacheService.getProductItemTest1()
    }

    /**
     * 영속성 컨텍스트에 같은 엔티티가 있더라도 식별자로 조회하지 않는다면 쿼리를 날림
     */
    @Test
    fun test2() {
        pmCacheService.getProductItemTest2()
    }

    /**
     * 트랜잭션이 다르다면 영속성 컨텍스트를 공유하지 않으므로 각자 2번 쿼리
     */
    @Test
    fun test3() {
        pmCacheService.getProductItemTest3_1()
        pmCacheService.getProductItemTest3_1()
    }
}