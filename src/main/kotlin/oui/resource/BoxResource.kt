package oui.resource

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import oui.resource.BoxUriConstants.OPTIMIZE
import oui.resource.BoxUriConstants.URI
import oui.resource.BoxUriConstants.SEQ
import oui.service.BoxService
import reactor.core.publisher.toMono
import java.util.stream.Collectors.joining

@RestController
@RequestMapping(URI)
class BoxResource(private val boxService: BoxService) {

    @GetMapping("$OPTIMIZE$SEQ")
    fun optimize(@PathVariable seq: String) =
            boxService.optimize(seq)
                    .map { it.toString() }
                    .collect(joining("/"))
                    .map { BoxOptimized(seq, it) }
                    .toMono()

    data class BoxOptimized(val entry: String, val optimized: String)
}