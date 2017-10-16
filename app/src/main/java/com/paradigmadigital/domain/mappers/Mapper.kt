package com.paradigmadigital.domain.mappers

interface Mapper<OUT, IN> {
    fun map(input: IN): OUT
}
