package com.bank.bank.controller

import com.bank.bank.dto.`in`.CicilanIn
import com.bank.bank.dto.out.CicilanOut
import com.bank.bank.dto.out.ResponseDto
import com.bank.bank.model.Cicilan
import com.bank.bank.service.CicilanService
import org.springframework.transaction.annotation.Transactional
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/cicilan")
class CicilanController(private val cicilanService: CicilanService) {

    @PostMapping
    @Transactional
    fun insertCicilan(@RequestBody cicilan: CicilanIn): ResponseDto<CicilanOut> {
        val insCicilan = cicilanService.insertCicilan(cicilan)

        if (insCicilan != null) {
            return ResponseDto("Insert data cicilan berhasil", insCicilan) // Jika data tidak ditemukan
        }
        return ResponseDto("Insert data cicilan gagal", null)
    }

}