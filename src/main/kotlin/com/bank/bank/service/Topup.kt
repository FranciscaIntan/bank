package com.bank.bank.service

import com.bank.bank.dto.`in`.TopupIn
import com.bank.bank.dto.out.TopupOut
import com.bank.bank.model.Pinjaman
import com.bank.bank.model.Topup
import com.bank.bank.repository.TopupRepository
import com.bank.bank.repository.PinjamanRepository
import org.springframework.stereotype.Service
import java.sql.Timestamp

@Service
class TopupService(
    private val topupRepository: TopupRepository,
    private val pinjamanRepository: PinjamanRepository
) {

    fun insertTopup(topup: TopupIn): TopupOut? {
        if (topup.nomorFaktur != "" && topup.nikKtp != "") {
            val pinjaman = pinjamanRepository.findAllByNikKtpAndNomorFaktur(topup.nikKtp, topup.nomorFaktur)
            if (pinjaman.isPresent) {
                if (pinjaman.get().statusPinjaman == "Berjalan" && pinjaman.get().sisaPinjaman < pinjaman.get().jumlahPinjaman) {
                    val sisaPinjaman = pinjaman.get().sisaPinjaman + topup.nominalTopup
                    if (pinjaman.get().jumlahPinjaman >= sisaPinjaman) {
                        val now = Timestamp(System.currentTimeMillis())
                        var topupIns = Topup()
                        topupIns.nomorFaktur = topup.nomorFaktur
                        topupIns.nikKtp = topup.nikKtp
                        topupIns.nominalTopup = topup.nominalTopup
                        topupIns.createdAt = now
                        topupIns.updatedAt = now
                        topupIns.pinjaman = pinjaman.get()
                        val topupIn = topupRepository.saveAndFlush(topupIns)
                        val topupOut = TopupOut()
                        topupOut.nomorFaktur = topupIn.nomorFaktur
                        topupOut.nikKtp = topupIn.nikKtp
                        topupOut.nominalTopup = topupIn.nominalTopup
                        topupOut.id = topupIn.id
                        pinjaman.get().sisaPinjaman = sisaPinjaman
                        pinjaman.get().updatedAt = now
                        pinjamanRepository.save(pinjaman.get())
                        return topupOut
                    }
                }
            } else {
                return null
            }
        }
        return null
    }
}