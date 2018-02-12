package oui.service

import org.springframework.stereotype.Service
import oui.ext.append
import oui.ext.toFlux
import reactor.core.publisher.Flux
import java.util.Comparator.reverseOrder

@Service
class BoxService {

    companion object {
        private const val boxMaxSize: Int = 10
    }

    fun optimize(seq: String, boxes: MutableList<Box> = mutableListOf()) =
            (0..seq.length).toFlux()
                    .map { seq[it] }
                    .switchIfEmpty(Flux.error(IllegalArgumentException("Required non blank box sequence")))
                    .map { it.toString() }
                    .map { it.toInt() }
                    .sort(reverseOrder())
                    .doOnNext { fill(it, boxes) }
                    .last()
                    .map { boxes }
                    .flatMapIterable { it }

    fun fill(item: Int, boxes: MutableList<Box>): List<Box> =
            if (itemFitsInABoxThenAdd(boxes, item)) boxes
            else createNewBoxAndAddItem(boxes, item)

    private fun itemFitsInABoxThenAdd(boxes: MutableList<Box>, item: Int) =
            boxes.stream()
                    .filter { it.fitsIn(item) }
                    .peek { it.add(item) }
                    .findAny()
                    .isPresent

    private fun createNewBoxAndAddItem(boxes: MutableList<Box>, item: Int) = boxes.append(Box(item))

    class Box(private val items: MutableList<Int> = mutableListOf()) {
        constructor(vararg item: Int) : this(item.toMutableList())

        fun add(item: Int) = if (item <= boxMaxSize) items.add(item) else throw IllegalArgumentException()
        private fun sum() = items.sum()
        fun fitsIn(item: Int) = item + sum() <= boxMaxSize

        override fun toString() = items.joinToString("")
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            other as Box
            if (items != other.items) return false
            return true
        }

        override fun hashCode() = items.hashCode()
    }
}