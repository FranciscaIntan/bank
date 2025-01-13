package com.bank.bank.dto.out

data class ResponseDto<T>(
    val msg: String,
    val data: T?
)
