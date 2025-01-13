package com.bank.bank.dto.out

import com.bank.bank.model.AuditColumns
import com.bank.bank.model.Pinjaman
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*


class TopupOut {
    var id: Long = 0
    var nomorFaktur: String = ""
    var nikKtp: String = ""
    var nominalTopup: Int = 0
}