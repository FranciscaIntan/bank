package com.bank.bank.model

import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import org.hibernate.annotations.DynamicUpdate
import java.sql.Timestamp

@MappedSuperclass
@DynamicUpdate
class AuditColumns {

    var createdAt: Timestamp? = null
    var updatedAt: Timestamp? = null

    @PrePersist
    fun prePersist() {
        if(createdAt == null) {
            createdAt = Timestamp(System.currentTimeMillis())
        }
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = Timestamp(System.currentTimeMillis())
    }

}