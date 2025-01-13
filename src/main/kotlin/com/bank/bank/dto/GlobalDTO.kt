package com.bank.bank.dto

data class ResponseDto<T>(
    val msg: String,
    val data: T?
)
