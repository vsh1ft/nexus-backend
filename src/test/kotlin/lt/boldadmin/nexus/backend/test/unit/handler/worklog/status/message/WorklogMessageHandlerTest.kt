package lt.boldadmin.nexus.backend.test.unit.handler.worklog.status.message

import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.verify
import lt.boldadmin.nexus.api.service.worklog.status.message.WorklogMessageService
import lt.boldadmin.nexus.api.type.valueobject.Message
import lt.boldadmin.nexus.backend.handler.worklog.status.message.WorklogMessageHandler
import lt.boldadmin.nexus.backend.route.Routes
import lt.boldadmin.nexus.backend.test.unit.handler.create
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.lenient
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.toMono
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class WorklogMessageHandlerTest {

    @Mock
    private lateinit var worklogMessageServiceSpy: WorklogMessageService

    private lateinit var webClient: WebTestClient

    @Before
    fun `Set up`() {
        val contextStub = create()
        lenient()
            .`when`(contextStub.getBean(WorklogMessageHandler::class.java))
            .doReturn(WorklogMessageHandler(worklogMessageServiceSpy))

        webClient = WebTestClient.bindToRouterFunction(Routes(contextStub).router()).build()
    }


    @Test
    fun `Logs work by message`() {
        val message = Message("1234", "3333", "hello there")

        webClient.post()
            .uri("/worklog/status/log-work/message")
            .body(message.toMono(), message.javaClass)
            .exchange()
            .expectStatus()
            .isOk
            .expectBody()
            .isEmpty

        argumentCaptor<Message>().apply {
            verify(worklogMessageServiceSpy).logWork(capture())
            assertEquals(message, firstValue)
        }
    }
}
