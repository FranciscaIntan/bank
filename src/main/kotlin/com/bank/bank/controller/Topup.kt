package com.bank.bank.controller

import com.bank.bank.model.Cicilan
import com.bank.bank.model.Topup
import com.bank.bank.service.CicilanService
import com.bank.bank.service.TopupService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/topup")
class TopupController(private val topupService: TopupService) {

    //    @PostMapping
//    fun insertTopup(@RequestBody topup: Topup): Any = topupService.insertTopup(topup)
    @PostMapping
    fun insertTopup(@RequestBody topup: Topup): ResponseEntity<Any> {
        val insTopup = topupService.insertTopup(topup)

        if (insTopup is String) {
            return ResponseEntity("Insert data Topup gagal", HttpStatus.BAD_REQUEST) // Jika data tidak ditemukan
        }
        return ResponseEntity(insTopup, HttpStatus.OK)
    }
}