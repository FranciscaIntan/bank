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
            val pinjaman = pinjamanRepository.findOneByNikKtpAndNomorFaktur(cicilan.nikKtp, cicilan.nomorFaktur)
            if (pinjaman != null) {
                if (cicilan.nominalCicilan <= pinjaman.sisaPinjaman) {
                    var statusPinjaman = pinjaman.statusPinjaman
                    var sisaPinjaman = pinjaman.sisaPinjaman
                    if (sisaPinjaman != null) {
                        if (pinjaman.sisaPinjaman != cicilan.nominalCicilan) {
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
                    ci.pinjaman = pinjaman
                    var cicilanIns = cicilanRepository.saveAndFlush(ci)
                    sisaPinjaman -= cicilan.nominalCicilan
                    var outCicilan = CicilanOut()
                    outCicilan.nomorFaktur = cicilanIns.nomorFaktur
                    outCicilan.nikKtp = cicilan.nikKtp
                    outCicilan.nominalCicilan = cicilan.nominalCicilan
                    pinjaman.statusPinjaman = statusPinjaman
                    pinjaman.sisaPinjaman = sisaPinjaman
                    pinjaman.updatedAt = now
                    pinjamanRepository.save(pinjaman)
                    return outCicilan
                }
            } else {
                return null
            }
        }
        return null
    }
}