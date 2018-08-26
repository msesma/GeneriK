package eu.sesma.generik.platform

import java.util.*

fun Float.format(digits: Int) = java.lang.String.format(Locale.ENGLISH, "%.${digits}f", this)

fun <T : Collection<*>> T?.isNullOrEmpty(): Boolean {
    return this == null || this.isEmpty()
}
