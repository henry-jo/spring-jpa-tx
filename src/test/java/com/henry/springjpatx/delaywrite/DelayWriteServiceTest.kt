package com.henry.springjpatx.delaywrite

import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.Test

@RunWith(SpringRunner::class)
@SpringBootTest
class DelayWriteServiceTest {

    @Autowired
    private lateinit var delayWriteService: DelayWriteService

    @Test
    fun test1() {
        delayWriteService.test1()
    }

    @Test
    fun test2() {
        delayWriteService.test2()
    }

    @Test
    fun test3() {
        delayWriteService.test3()
    }
}