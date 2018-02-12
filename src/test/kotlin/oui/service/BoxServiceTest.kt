package oui.service

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.test.context.junit.jupiter.SpringExtension
import oui.service.BoxService.Box
import reactor.test.test

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class BoxServiceTest {

    @Autowired
    private lateinit var boxService: BoxService

    @Test
    fun `optimize with default sequence should optimize`() {
        boxService.optimize("163841689525773").test()
                .expectNext(Box(9, 1), Box(8, 2), Box(8, 1), Box(7, 3), Box(7, 3), Box(6, 4), Box(6), Box(5, 5))
                .expectComplete()
                .verify()
    }
    @Test
    fun `optimize with blank sequence should throw exception`() {
        boxService.optimize("").test()
                .expectNext(Box(9, 1), Box(8, 2), Box(8, 1), Box(7, 3), Box(7, 3), Box(6, 4), Box(6), Box(5, 5))
                .expectComplete()
                .verify()
    }
}