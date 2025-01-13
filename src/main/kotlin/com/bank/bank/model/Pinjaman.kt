package com.bank.bank.model

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

@Entity
class Pinjaman: AuditColumns() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    var id: Long = 0

    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    var namaNasabah: String = ""

    @Column(columnDefinition = "VARCHAR(16)", nullable = false)
    var nikKtp: String = ""

    @Column(columnDefinition = "BIGINT UNSIGNED", nullable = false)
    var jumlahPinjaman: Int = 0

    @Column(columnDefinition = "BIGINT UNSIGNED", nullable = false)
    var lamaPinjaman: Int = 0

    @Column(columnDefinition = "VARCHAR(15)", nullable = false)
    var nomorFaktur: String? = ""

    var tanggalJatuhTempo: Timestamp? = null

    @OneToMany(mappedBy = "pinjaman", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonIgnore
    var cicilan: MutableList<Cicilan> = mutableListOf()

    @Column(columnDefinition = "VARCHAR(10)", nullable = false)
    var statusPinjaman: String = ""

    @Column(columnDefinition = "BIGINT UNSIGNED", nullable = false)
    var sisaPinjaman: Int = 0

    @OneToMany(mappedBy = "pinjaman", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonIgnore
    var topup: MutableList<Topup> = mutableListOf()

}