package com.bank.bank.dto.out

import com.bank.bank.model.AuditColumns
import com.bank.bank.model.Cicilan
import com.bank.bank.model.Topup
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import java.sql.Timestamp
import java.util.Date

class PinjamanOut{
    var id: Long = 0
    var namaNasabah: String = ""
    var nikKtp: String = ""
    var jumlahPinjaman: Int = 0
    var lamaPinjaman: Int = 0
    var nomorFaktur: String? = ""
    var tanggalJatuhTempo: Timestamp? = null
    var cicilan: MutableList<Cicilan> = mutableListOf()
    var statusPinjaman: String = ""
    var sisaPinjaman: Int = 0
    var topup: MutableList<Topup> = mutableListOf()

}