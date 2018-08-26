package eu.sesma.generik.domain.mappers

interface Mapper<OUT, IN> {
    fun map(input: IN): OUT
}
