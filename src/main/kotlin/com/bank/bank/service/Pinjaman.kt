package com.bank.bank.service

import com.bank.bank.model.Pinjaman
import com.bank.bank.repository.PinjamanRepository
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Optional

@Service
class PinjamanService(private val pinjamanRepository: PinjamanRepository) {
    fun insertPinjaman(pinjaman: Pinjaman): Pinjaman {

        val now = Timestamp(System.currentTimeMillis())
        val countFaktur = pinjamanRepository.CheckPinjamanCountToday(Timestamp(System.currentTimeMillis())) + 1
        val formattedNumber = String.format("%03d", countFaktur)
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val formattedDate = today.format(formatter)
        val nomorFaktur = formattedDate + formattedNumber
        val jatuhTempo = today.plusDays(pinjaman.lamaPinjaman.toLong())
        val pinjamanCount = jatuhTempo.atStartOfDay(ZoneId.systemDefault()).toInstant()

        pinjamanRepository.InsertPinjaman(
            pinjaman.namaNasabah, pinjaman.nikKtp,
            pinjaman.jumlahPinjaman, pinjaman.lamaPinjaman, nomorFaktur,
            jatuhTempo, now, now, "Berjalan" , pinjaman.jumlahPinjaman
        )
        val id = pinjamanRepository.getLastInsertId()
        pinjaman.id = id
        pinjaman.tanggalJatuhTempo = Timestamp.from(pinjamanCount)
        pinjaman.nomorFaktur = nomorFaktur
        pinjaman.sisaPinjaman = pinjaman.jumlahPinjaman
        pinjaman.statusPinjaman = "Berjalan"
        pinjaman.createdAt = now
        pinjaman.updatedAt = now
        return pinjaman
    }

    fun viewPinjamanByNik(nikKtp: String): Optional<Pinjaman> {
        return pinjamanRepository.ViewPinjamanByNIK(nikKtp)
    }

    fun viewJatuhTempoPinjaman(tanggalJatuhTempo: String): List<Pinjaman> {
        val localDate = LocalDate.parse(tanggalJatuhTempo)
        return pinjamanRepository.ViewJatuhTempoPinjaman(localDate, "Berjalan")
    }
}