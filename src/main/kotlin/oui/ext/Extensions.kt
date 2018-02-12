package oui.ext

import reactor.core.publisher.Flux

fun <T> MutableList<T>.append(item: T): MutableList<T> = run { add(item); return this }
fun <T> T.toFlux(): Flux<out T> = Flux.just(this)
fun IntRange.toFlux() = Flux.range(this.start, this.endInclusive)
