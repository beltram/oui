package oui.resource

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import oui.service.BoxService
import java.util.stream.Collectors.joining

@RestController
class BoxResource(private val boxService: BoxService) {

    @GetMapping("/optimize")
    fun optimize(@RequestParam seq: String) =
            boxService.optimize(seq)
                    .map { it.toString() }
                    .collect(joining("/"))
}