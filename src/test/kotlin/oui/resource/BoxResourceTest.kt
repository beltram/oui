package oui.resource

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.reactive.context.ReactiveWebApplicationContext
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import oui.resource.BoxResource.BoxOptimized
import oui.resource.BoxUriConstants.OPTIMIZE
import oui.resource.BoxUriConstants.URI

@SpringBootTest
@ExtendWith(SpringExtension::class)
class BoxResourceTest {

    private lateinit var testClient: WebTestClient

    private val defaultSeq = "163841689525773"

    @BeforeEach
    fun beforeEach(context: ReactiveWebApplicationContext) {
        testClient = WebTestClient.bindToApplicationContext(context)
                .configureClient()
                .build()
    }

    @Test
    fun `optimize resource with default sequence should optimize`() {
        testClient.get()
                .uri("$URI$OPTIMIZE/$defaultSeq")
                .exchange()
                .expectStatus().isOk
                .expectHeader().contentType(APPLICATION_JSON_UTF8)
                .expectBody()
                .consumeWith {
                    val r: BoxOptimized = jacksonObjectMapper().readValue(it.responseBody!!)
                    assertThat(r.entry).isEqualTo(defaultSeq)
                    assertThat(r.optimized).isEqualTo("91/82/81/73/73/64/6/55")
                }
    }
}
