package oui

import reactor.core.publisher.Flux
import reactor.test.StepVerifier

fun <T> Flux<T>.test() = StepVerifier.create(this)