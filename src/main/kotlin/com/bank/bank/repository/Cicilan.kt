package com.bank.bank.repository

import com.bank.bank.model.Cicilan
import com.bank.bank.model.Pinjaman
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.sql.Timestamp
import java.time.LocalDate

interface CicilanRepository : JpaRepository<Cicilan, Long> {

//    @Modifying
//    @Transactional
//    @Query("INSERT INTO cicilan (" +
//            "nomor_faktur, nik_ktp, nominal_cicilan, created_at, updated_at, pinjaman_id" +
//            ") VALUES (" +
//            ":nomorFaktur, :nikKtp, :nominalCicilan, :createdAt, :updatedAt, :pinjamanID)", nativeQuery = true)
//    fun InsertCicilan(
//        nomorFaktur: String, nikKtp: String, nominalCicilan: Int, createdAt: Timestamp, updatedAt: Timestamp, pinjamanID: Long
//    )
//
//    @Query("SELECT LAST_INSERT_ID()", nativeQuery = true)
//    fun getLastInsertId(): Long

}
