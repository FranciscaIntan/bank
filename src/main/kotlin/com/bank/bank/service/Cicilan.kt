package com.bank.bank.service

import com.bank.bank.dto.`in`.CicilanIn
import com.bank.bank.dto.out.CicilanOut
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

    fun insertCicilan(cicilan: CicilanIn): CicilanOut? {
        if (cicilan.nomorFaktur != "" && cicilan.nikKtp != "") {
            val pinjaman = pinjamanRepository.findAllByNikKtpAndNomorFaktur(cicilan.nikKtp, cicilan.nomorFaktur)
            if (pinjaman.isPresent) {
                if (cicilan.nominalCicilan <= pinjaman.get().sisaPinjaman) {
                    var statusPinjaman = pinjaman.get().statusPinjaman
                    var sisaPinjaman = pinjaman.get().sisaPinjaman
                    if (sisaPinjaman != null) {
                        if (pinjaman.get().sisaPinjaman != cicilan.nominalCicilan) {
                            if (cicilan.nominalCicilan % 50000 != 0) {
                                return null
                            }
                        } else {
                            statusPinjaman = "Lunas"
                        }
                    }
                    val now = Timestamp(System.currentTimeMillis())
                    var ci = Cicilan()
                    ci.nomorFaktur = cicilan.nomorFaktur
                    ci.nikKtp = cicilan.nikKtp
                    ci.nominalCicilan = cicilan.nominalCicilan
                    ci.createdAt = now
                    ci.updatedAt = now
                    ci.pinjaman = pinjaman.get()
                    var cicilanIns = cicilanRepository.saveAndFlush(ci)
                    sisaPinjaman -= cicilan.nominalCicilan
                    var outCicilan = CicilanOut()
                    outCicilan.nomorFaktur = cicilanIns.nomorFaktur
                    outCicilan.nikKtp = cicilan.nikKtp
                    outCicilan.nominalCicilan = cicilan.nominalCicilan
                    pinjaman.get().statusPinjaman = statusPinjaman
                    pinjaman.get().sisaPinjaman = sisaPinjaman
                    pinjaman.get().updatedAt = now
                    pinjamanRepository.save(pinjaman.get())
                    return outCicilan
                }
            } else {
                return null
            }
        }
        return null
    }
}