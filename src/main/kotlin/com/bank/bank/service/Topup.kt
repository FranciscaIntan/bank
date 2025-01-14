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
            val pinjaman = pinjamanRepository.findOneByNikKtpAndNomorFaktur(topup.nikKtp, topup.nomorFaktur)
            if (pinjaman == null) {
                return null
            }
            if (pinjaman.statusPinjaman == "Berjalan" && pinjaman.sisaPinjaman < pinjaman.jumlahPinjaman && topup.nominalTopup != 0) {
                val sisaPinjaman = pinjaman.sisaPinjaman + topup.nominalTopup
                if (pinjaman.jumlahPinjaman >= sisaPinjaman) {
                    val now = Timestamp(System.currentTimeMillis())
                    var topupIns = Topup()
                    topupIns.nomorFaktur = topup.nomorFaktur
                    topupIns.nikKtp = topup.nikKtp
                    topupIns.nominalTopup = topup.nominalTopup
                    topupIns.createdAt = now
                    topupIns.updatedAt = now
                    topupIns.pinjaman = pinjaman
                    val topupIn = topupRepository.saveAndFlush(topupIns)
                    val topupOut = TopupOut()
                    topupOut.nomorFaktur = topupIn.nomorFaktur
                    topupOut.nikKtp = topupIn.nikKtp
                    topupOut.nominalTopup = topupIn.nominalTopup
                    topupOut.id = topupIn.id
                    pinjaman.sisaPinjaman = sisaPinjaman
                    pinjaman.updatedAt = now
                    pinjamanRepository.save(pinjaman)
                    return topupOut
                }
            }

        }
        return null
    }
}