package com.henry.springjpatx.delaywrite

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/delay-write")
class DelayWriteController(
    private val delayWriteService: DelayWriteService
) {

    @GetMapping("/test1")
    fun test1() {
        delayWriteService.test1()
    }

    @GetMapping("/test2")
    fun test2() {
        delayWriteService.test2()
    }

    @GetMapping("/test3")
    fun test3() {
        delayWriteService.test3()
    }

    @GetMapping("/test4")
    fun test4() {
        delayWriteService.test4()
    }

    @GetMapping("/test5")
    fun test5() {
        delayWriteService.test5()
    }

    @GetMapping("/test6")
    fun test6() {
        delayWriteService.test6()
    }
}