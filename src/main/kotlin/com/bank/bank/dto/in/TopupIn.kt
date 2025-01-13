package com.bank.bank.dto.`in`

import com.bank.bank.model.AuditColumns
import com.bank.bank.model.Pinjaman
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*


class TopupIn {
    var nomorFaktur: String = ""
    var nikKtp: String = ""
    var nominalTopup: Int = 0
}