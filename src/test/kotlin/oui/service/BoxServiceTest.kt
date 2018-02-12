package oui.service

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import oui.service.BoxService.Box
import reactor.test.StepVerifier.Step
import reactor.test.expectError
import reactor.test.test

@SpringBootTest
@ExtendWith(SpringExtension::class)
class BoxServiceTest {

    @Autowired
    private lateinit var boxService: BoxService

    @Test
    fun `optimize with default sequence should optimize`() {
        boxService.optimize("163841689525773").test()
                .expectBox(9, 1)
                .expectBox(8, 2)
                .expectBox(8, 1)
                .expectBox(7, 3)
                .expectBox(7, 3)
                .expectBox(6, 4)
                .expectBox(6)
                .expectBox(5, 5)
                .expectComplete()
                .verify()
    }

    @Test
    fun `optimize with blank sequence should throw illegal argument exception`() {
        boxService.optimize("").test()
                .expectError(IllegalArgumentException::class)
                .verify()
    }

    fun Step<Box>.expectBox(vararg items: Int) = expectNext(Box(items.toMutableList()))
}