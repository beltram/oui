package oui.ext

import java.util.stream.Stream

fun <T> MutableList<T>.append(item: T): MutableList<T> = run { this.add(item); return this }
fun IntRange.stream(): Stream<Int> = this.toList().stream()