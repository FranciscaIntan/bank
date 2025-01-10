package com.bank.bank.service

import com.bank.bank.model.Cicilan
import com.bank.bank.model.Pinjaman
import com.bank.bank.repository.CicilanRepository
import com.bank.bank.repository.PinjamanRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.sql.Timestamp

@Service
class CicilanService(
    private val cicilanRepository: CicilanRepository,
    private val pinjamanRepository: PinjamanRepository
) {

    fun insertCicilan(cicilan: Cicilan): Any {
        if (cicilan.nomorFaktur != "" && cicilan.nikKtp != "") {
            val pinjaman = pinjamanRepository.ViewPinjamanByFakturAndNIK(cicilan.nikKtp, cicilan.nomorFaktur)
            if (!pinjaman.isEmpty) {
                if (cicilan.nominalCicilan <= pinjaman.get().sisaPinjaman) {
                    var statusPinjaman = pinjaman.get().statusPinjaman
                    var sisaPinjaman = pinjaman.get().sisaPinjaman
                    if (sisaPinjaman != null) {
                        if (pinjaman.get().sisaPinjaman != cicilan.nominalCicilan) {
                            if (cicilan.nominalCicilan % 50000 != 0) {
                                return "nominal tidak sesuai"
                            }
                        } else {
                            statusPinjaman = "Lunas"
                        }
                    }
                    val now = Timestamp(System.currentTimeMillis())
                    cicilanRepository.InsertCicilan(
                        cicilan.nomorFaktur, cicilan.nikKtp, cicilan.nominalCicilan, now, now, pinjaman.get().id
                    )
                    var id = cicilanRepository.getLastInsertId()
                    cicilan.id = id
                    sisaPinjaman -= cicilan.nominalCicilan
                    cicilan.pinjaman = pinjaman.get()
                    cicilan.createdAt = now
                    cicilan.updatedAt = now
                    pinjamanRepository.updateStatusDanSisaPinjaman(statusPinjaman, sisaPinjaman, now, pinjaman.get().id)
                    return cicilan
                }
            } else {
                return "inputan tidak sesuai"
            }
        }
        return "nominal tidak sesuai"
    }
}