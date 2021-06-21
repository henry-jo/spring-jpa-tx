package com.henry.springjpatx.propagation

import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronizationManager
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/propagation")
class PropagationController(
    private val propagationTestService: PropagationTestService
) {
    @GetMapping("/test1")
    fun test1() {
        propagationTestService.propagationTest1()
    }

    @GetMapping("/test2")
    fun test2() {
        propagationTestService.propagationTest2()
    }

    @GetMapping("/test3")
    fun test3() {
        propagationTestService.propagationTest3()
    }

    @GetMapping("/test4")
    fun test4() {
        propagationTestService.propagationTest4()
    }

    @GetMapping("/test5")
    @Transactional
    fun test5() {
        println(TransactionSynchronizationManager.isCurrentTransactionReadOnly())

        propagationTestService.propagationTest5()
    }

    @GetMapping("/test6")
    @Transactional
    fun test6() {
        println(TransactionSynchronizationManager.isCurrentTransactionReadOnly())

        propagationTestService.propagationTest5()
    }
}
