package com.bank.bank.repository

import com.bank.bank.model.Topup
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.sql.Timestamp

interface TopupRepository : JpaRepository<Topup, Long> {

//    @Modifying
//    @Transactional
//    @Query("INSERT INTO topup (" +
//            "nomor_faktur, nik_ktp, nominal_topup, created_at, updated_at, pinjaman_id" +
//            ") VALUES (" +
//            ":nomorFaktur, :nikKtp, :nominalTopup, :createdAt, :updatedAt, :pinjamanID)", nativeQuery = true)
//    fun InsertTopup(
//        nomorFaktur: String, nikKtp: String, nominalTopup: Int, createdAt: Timestamp, updatedAt: Timestamp, pinjamanID: Long
//    )
//
//    @Query("SELECT LAST_INSERT_ID()", nativeQuery = true)
//    fun getLastInsertId(): Long

}
