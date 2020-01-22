package com.henry.springjpatx.dirtychecking

import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.Test

@RunWith(SpringRunner::class)
@SpringBootTest
class DirtyCheckingServiceTest {

    @Autowired
    private lateinit var dirtyCheckingService: DirtyCheckingService

    @Test
    fun dirtyCheckingTest1() {
        dirtyCheckingService.test1()
    }
}