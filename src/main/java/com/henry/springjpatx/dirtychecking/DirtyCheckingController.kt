package com.henry.springjpatx.dirtychecking

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/dirty-checking")
class DirtyCheckingController(
    private val dirtyCheckingService: DirtyCheckingService
) {

    @GetMapping("/test1")
    fun test1() {
        dirtyCheckingService.test1()
    }

    @GetMapping("/test2")
    fun test2() {
        dirtyCheckingService.test2()
    }

    @GetMapping("/test3")
    fun test3() {
        dirtyCheckingService.test3()
    }

    @GetMapping("/test4")
    fun test4() {
        dirtyCheckingService.test4()
    }

    @GetMapping("/test5")
    fun test5() {
        dirtyCheckingService.test5()
    }
}