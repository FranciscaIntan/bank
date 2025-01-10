package com.bank.bank.controller

import com.bank.bank.model.Cicilan
import com.bank.bank.service.CicilanService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/cicilan")
class CicilanController(private val cicilanService: CicilanService) {

//    @PostMapping
//    fun insertCicilan(@RequestBody cicilan: Cicilan): Any = cicilanService.insertCicilan(cicilan)

    @PostMapping
    fun insertCicilan(@RequestBody cicilan: Cicilan): ResponseEntity<Any> {
        val insCicilan = cicilanService.insertCicilan(cicilan)

        if (insCicilan is String) {
            return ResponseEntity("Insert data Cicilan gagal", HttpStatus.BAD_REQUEST) // Jika data tidak ditemukan
        }
        return ResponseEntity(insCicilan, HttpStatus.OK)
    }

}