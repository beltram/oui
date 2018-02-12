package oui.service.ext

import reactor.core.publisher.Flux

fun <T> MutableList<T>.append(item: T): MutableList<T> = kotlin.run { this.add(item); return this }

fun <T> List<T>.toto(): Flux<out T> = Flux.fromIterable(this)