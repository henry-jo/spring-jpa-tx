package com.henry.springjpatx.propagation

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
}