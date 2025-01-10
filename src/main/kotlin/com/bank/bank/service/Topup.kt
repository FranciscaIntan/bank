package com.bank.bank.service

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

    fun insertTopup(topup: Topup): Any {
        if (topup.nomorFaktur != "" && topup.nikKtp != "") {
            val pinjaman = pinjamanRepository.ViewPinjamanByFakturAndNIK(topup.nikKtp, topup.nomorFaktur)
            if (!pinjaman.isEmpty) {
                if (pinjaman.get().statusPinjaman == "Berjalan" && pinjaman.get().sisaPinjaman < pinjaman.get().jumlahPinjaman) {
                    val sisaPinjaman = pinjaman.get().sisaPinjaman + topup.nominalTopup
                    if (pinjaman.get().jumlahPinjaman <= sisaPinjaman) {
                        val now = Timestamp(System.currentTimeMillis())
                        topupRepository.InsertTopup(
                            topup.nomorFaktur,
                            topup.nikKtp,
                            topup.nominalTopup,
                            now,
                            now,
                            pinjaman.get().id
                        )
                        pinjamanRepository.updateStatusDanSisaPinjaman(
                            pinjaman.get().statusPinjaman,
                            sisaPinjaman,
                            now,
                            pinjaman.get().id
                        )
                        var id = topupRepository.getLastInsertId()
                        topup.id = id
                        topup.createdAt = now
                        topup.updatedAt = now
                        return topup
                    }
                }
            } else {
                return "inputan tidak sesuai"
            }
        }
        return "nomial berlebih"
    }
}