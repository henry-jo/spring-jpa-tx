package com.henry.springjpatx.rollbackmarking

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/rollback-marking")
class RollBackMarkingController(
    private val rollBackMarkingTestService: RollBackMarkingTestService
) {
    @GetMapping("/test1")
    fun test1() {
        rollBackMarkingTestService.test1()
    }

    @GetMapping("/test2")
    fun test2() {
        rollBackMarkingTestService.test2()
    }
}