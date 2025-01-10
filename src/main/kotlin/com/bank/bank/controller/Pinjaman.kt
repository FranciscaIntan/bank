package com.bank.bank.controller

import com.bank.bank.model.Pinjaman
import com.bank.bank.service.PinjamanService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/pinjaman")
class PinjamanController(private val pinjamanService: PinjamanService) {

    //    @GetMapping("/nik/{nik}")
    //    fun getUser(@PathVariable nik: String): Optional<Pinjaman> = pinjamanService.viewPinjamanByNik(nik)
    //    @GetMapping("/jatuh-tempo/{jatuhTempo}")
    //    fun ViewJatuhTempoPinjaman(@PathVariable jatuhTempo: String): List<Pinjaman> = pinjamanService.viewJatuhTempoPinjaman(jatuhTempo)
    //    @PostMapping
    //    fun insertPinjaman(@RequestBody pinjaman: Pinjaman): Pinjaman = pinjamanService.insertPinjaman(pinjaman)

    @PostMapping
    fun insertPinjaman(@RequestBody pinjaman: Pinjaman): ResponseEntity<Any> {
        val insetPinjaman = pinjamanService.insertPinjaman(pinjaman)

        if (insetPinjaman != null) {
            return ResponseEntity(insetPinjaman, HttpStatus.OK) // Mengembalikan body dengan status OK
        } else {
            return ResponseEntity("Insert data pinjaman gagal", HttpStatus.BAD_REQUEST) // Jika data tidak ditemukan
        }
    }

    @GetMapping("/nik/{nik}")
    fun getUser(@PathVariable nik: String): ResponseEntity<Any> {
        val pinjaman = pinjamanService.viewPinjamanByNik(nik)

        if (!pinjaman.isEmpty) {
            return ResponseEntity(pinjaman.get(), HttpStatus.OK) // Mengembalikan body dengan status OK
        } else {
            return ResponseEntity("Yah data $nik tidak ada", HttpStatus.BAD_REQUEST) // Jika data tidak ditemukan
        }
    }

    @GetMapping("/jatuh-tempo/{jatuhTempo}")
    fun ViewJatuhTempoPinjaman(@PathVariable jatuhTempo: String): ResponseEntity<Any> {
      val daftarPinjaman = pinjamanService.viewJatuhTempoPinjaman(jatuhTempo)

        if (!daftarPinjaman.isEmpty()) {
            return ResponseEntity(daftarPinjaman, HttpStatus.OK)
        } else {
            return ResponseEntity("Tidak ada daftar pinjaman yang jatuh tempo", HttpStatus.BAD_REQUEST)
        }
    }

}