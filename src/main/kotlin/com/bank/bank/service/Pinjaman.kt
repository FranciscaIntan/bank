package com.bank.bank.service

import com.bank.bank.model.Pinjaman
import com.bank.bank.repository.PinjamanRepository
import com.bank.bank.dto.`in`.PinjamanIn
import com.bank.bank.dto.out.PinjamanOut
import com.bank.bank.model.Cicilan
import com.bank.bank.model.Topup
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.Duration
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Optional

@Service
class PinjamanService(private val pinjamanRepository: PinjamanRepository) {
    fun insertPinjaman(pinjaman: PinjamanIn): PinjamanOut {
        val now = Timestamp(System.currentTimeMillis())
        val pinj = now.toInstant().plus(Duration.ofDays(pinjaman.lamaPinjaman.toLong()))
        var pin = Pinjaman()
        pin.namaNasabah = pinjaman.namaNasabah
        pin.nikKtp = pinjaman.nikKtp
        pin.lamaPinjaman = pinjaman.lamaPinjaman
        pin.jumlahPinjaman = pinjaman.jumlahPinjaman
        pin.tanggalJatuhTempo = Timestamp.from(pinj)
        pin.nomorFaktur = getFakturNumber(pinjaman)
        pin.sisaPinjaman = pinjaman.jumlahPinjaman
        pin.statusPinjaman = "Berjalan"
        pin.createdAt = now
        pin.updatedAt = now
        val pinjaman = pinjamanRepository.saveAndFlush(pin)
        var pinOut = PinjamanOut()
        pinOut.id = pinjaman.id
        pinOut.namaNasabah = pinjaman.namaNasabah
        pinOut.nikKtp = pinjaman.nikKtp
        pinOut.jumlahPinjaman = pinjaman.jumlahPinjaman
        pinOut.lamaPinjaman = pinjaman.lamaPinjaman
        pinOut.nomorFaktur = pinjaman.nomorFaktur
        pinOut.tanggalJatuhTempo = pinjaman.tanggalJatuhTempo
        pinOut.cicilan = pinjaman.cicilan
        pinOut.statusPinjaman = pinjaman.statusPinjaman
        pinOut.sisaPinjaman = pinjaman.sisaPinjaman
        pinOut.topup = pinjaman.topup
        return pinOut
    }

    fun viewPinjamanByNik(nikKtp: String): PinjamanOut? {
        val pinjaman = pinjamanRepository.findOneByNikKtp(nikKtp)
        if (pinjaman == null) {
            return null
        }
        var pinOut = PinjamanOut()
        pinOut.id = pinjaman.id
        pinOut.namaNasabah = pinjaman.namaNasabah
        pinOut.nikKtp = pinjaman.nikKtp
        pinOut.jumlahPinjaman = pinjaman.jumlahPinjaman
        pinOut.lamaPinjaman = pinjaman.lamaPinjaman
        pinOut.nomorFaktur = pinjaman.nomorFaktur
        pinOut.tanggalJatuhTempo = pinjaman.tanggalJatuhTempo
        pinOut.cicilan = pinjaman.cicilan
        pinOut.statusPinjaman = pinjaman.statusPinjaman
        pinOut.sisaPinjaman = pinjaman.sisaPinjaman
        pinOut.topup = pinjaman.topup
        return pinOut
    }

    fun viewJatuhTempoPinjaman(tanggalJatuhTempo: String): List<Pinjaman> {
        val localDate = LocalDate.parse(tanggalJatuhTempo)
        return pinjamanRepository.ViewJatuhTempoPinjaman(localDate, "Berjalan")
    }

    fun getFakturNumber(pinjaman: PinjamanIn): String {
        val countFaktur = pinjamanRepository.CheckPinjamanCountToday(Timestamp(System.currentTimeMillis())) + 1
        val formattedNumber = String.format("%03d", countFaktur)
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val formattedDate = today.format(formatter)
        val nomorFaktur = formattedDate + formattedNumber
        return nomorFaktur
    }
}