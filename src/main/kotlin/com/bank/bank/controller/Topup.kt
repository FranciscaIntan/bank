package com.bank.bank.controller

import com.bank.bank.dto.`in`.TopupIn
import com.bank.bank.dto.out.ResponseDto
import com.bank.bank.dto.out.TopupOut
import com.bank.bank.model.Cicilan
import com.bank.bank.model.Topup
import com.bank.bank.service.CicilanService
import com.bank.bank.service.TopupService
import org.springframework.transaction.annotation.Transactional
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/topup")
class TopupController(private val topupService: TopupService) {

    @PostMapping
    @Transactional
    fun insertTopup(@RequestBody topup: TopupIn): ResponseDto<TopupOut> {
        val insTopup = topupService.insertTopup(topup)

        if (insTopup != null) {
            return ResponseDto("Insert data topup berhasil", insTopup)
        }
        return ResponseDto("Insert data Topup gagal",null)
    }
}