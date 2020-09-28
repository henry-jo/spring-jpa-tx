package com.henry.springjpatx.txsyncmanager

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/tx-sync-manager")
class TxSyncManagerController(
    private val txSyncManagerService: TxSyncManagerService
) {
    @GetMapping("/test1")
    fun test1() {
        txSyncManagerService.test1()
    }

    @GetMapping("/test2")
    fun test2() {
        txSyncManagerService.test2()
    }
}