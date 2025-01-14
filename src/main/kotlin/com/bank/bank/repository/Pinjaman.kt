package com.bank.bank.repository

import com.bank.bank.model.Pinjaman
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.sql.Timestamp
import java.time.LocalDate
import java.util.Optional

interface PinjamanRepository : JpaRepository<Pinjaman, Long> {

//    @Modifying
//    @Transactional
//    @Query(
//        "INSERT INTO pinjaman (" +
//                "nama_nasabah, nik_ktp, jumlah_pinjaman, lama_pinjaman, nomor_faktur, tanggal_jatuh_tempo, " +
//                "created_at, updated_at, status_pinjaman, sisa_pinjaman" +
//                ") VALUES (" +
//                ":namaNasabah, :nikKtp, :jumlahPinjaman, :lamaPinjaman, :nomorFaktur, :tanggalJatuhTempo, " +
//                ":createdAt, :updatedAt, :statusPinjaman, :sisaPinjaman)", nativeQuery = true
//    )
//    fun InsertPinjaman(
//        namaNasabah: String, nikKtp: String, jumlahPinjaman: Int,
//        lamaPinjaman: Int, nomorFaktur: String, tanggalJatuhTempo: LocalDate,
//        createdAt: Timestamp, updatedAt: Timestamp, statusPinjaman: String, sisaPinjaman: Int
//    )
    //    @Query("SELECT p FROM Pinjaman p WHERE p.nikKtp = :nikKtp")
//    fun ViewPinjamanByNIK(nikKtp: String): Optional<Pinjaman>
//    @Query("SELECT p FROM Pinjaman p WHERE p.nikKtp = :nikKtp AND p.nomorFaktur = :nomorFaktur")
//    fun ViewPinjamanByFakturAndNIK(nikKtp: String, nomorFaktur: String): Optional<Pinjaman>
//    @Query("SELECT LAST_INSERT_ID()", nativeQuery = true)
//    fun getLastInsertId(): Long
    //    @Modifying
//    @Transactional
//    @Query("UPDATE Pinjaman SET statusPinjaman = :statusPinjaman, sisaPinjaman = :sisaPinjaman," +
//            " updatedAt = :updatedAt WHERE id = :id")
//    fun updateStatusDanSisaPinjaman(statusPinjaman: String?, sisaPinjaman: Int?, updatedAt: Timestamp, id: Long)


    @Query("SELECT COUNT(id) FROM Pinjaman WHERE DATE(createdAt) = DATE(:createdAt)")
    fun CheckPinjamanCountToday(createdAt: Timestamp): Long

    fun findOneByNikKtp(nikKtp: String): Pinjaman?

    fun findOneByNikKtpAndNomorFaktur(nikKtp: String, nomorFaktur: String): Pinjaman?

    @Query("SELECT p FROM Pinjaman p WHERE DATE(p.tanggalJatuhTempo) >= :tanggalJatuhTempo AND p.statusPinjaman = :statusPinjaman")
    fun ViewJatuhTempoPinjaman(tanggalJatuhTempo: LocalDate, statusPinjaman: String): List<Pinjaman>

}