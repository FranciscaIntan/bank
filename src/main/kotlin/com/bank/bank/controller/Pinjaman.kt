package com.bank.bank.controller

import com.bank.bank.dto.out.ResponseDto
import com.bank.bank.dto.`in`.PinjamanIn
import com.bank.bank.dto.out.PinjamanOut
import com.bank.bank.service.PinjamanService
import org.springframework.web.bind.annotation.*
import org.springframework.transaction.annotation.Transactional

@RestController
@RequestMapping("/v1/pinjaman")
class PinjamanController(private val pinjamanService: PinjamanService) {
    @PostMapping
    @Transactional
    fun insertPinjaman(@RequestBody pinjaman: PinjamanIn): ResponseDto<PinjamanOut> {
        val insetPinjaman = pinjamanService.insertPinjaman(pinjaman)
        if (insetPinjaman != null) {
            return ResponseDto("Data berhasil ditambahkan", insetPinjaman)
        } else {
            return ResponseDto("Insert data pinjaman gagal", null)
        }
    }

    @GetMapping("/nik/{nik}")
    @Transactional(readOnly = true)
    fun getPinjaman(@PathVariable nik: String): ResponseDto<PinjamanOut> {
        val pinjaman = pinjamanService.viewPinjamanByNik(nik)

        if (pinjaman != null) {
            return ResponseDto("Data berhasil diambil", pinjaman)
        } else {
            return ResponseDto("Yah data $nik tidak ada", null)
        }
    }

    @GetMapping("/jatuh-tempo/{jatuhTempo}")
    @Transactional(readOnly = true)
    fun ViewJatuhTempoPinjaman(@PathVariable jatuhTempo: String): ResponseDto<Any> {
      val daftarPinjaman = pinjamanService.viewJatuhTempoPinjaman(jatuhTempo)

        if (!daftarPinjaman.isEmpty()) {
            return ResponseDto("Data berhasil diambil", daftarPinjaman)
        } else {
            return ResponseDto("Tidak ada daftar pinjaman yang jatuh tempo", null)
        }
    }

}