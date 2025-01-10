package com.bank.bank.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
class Cicilan: AuditColumns() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    var id: Long = 0

    var nomorFaktur: String = ""
    var nikKtp: String = ""
    var nominalCicilan: Int = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pinjaman_id", nullable = false)
    @JsonIgnore
    lateinit var pinjaman: Pinjaman

}