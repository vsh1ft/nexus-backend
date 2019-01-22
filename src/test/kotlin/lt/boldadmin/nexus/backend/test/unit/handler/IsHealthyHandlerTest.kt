package lt.boldadmin.nexus.backend.test.unit.handler

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import lt.boldadmin.nexus.backend.handler.*
import lt.boldadmin.nexus.backend.handler.worklog.WorklogAuthHandler
import lt.boldadmin.nexus.backend.handler.worklog.WorklogHandler
import lt.boldadmin.nexus.backend.handler.worklog.duration.WorklogDurationHandler
import lt.boldadmin.nexus.backend.handler.worklog.status.WorklogDescriptionHandler
import lt.boldadmin.nexus.backend.handler.worklog.status.WorklogStartEndHandler
import lt.boldadmin.nexus.backend.handler.worklog.status.location.WorklogLocationHandler
import lt.boldadmin.nexus.backend.handler.worklog.status.message.WorklogMessageHandler
import lt.boldadmin.nexus.backend.route.Routes
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.context.support.AbstractApplicationContext
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
class IsHealthyHandlerTest {

    @Mock
    private lateinit var contextStub: AbstractApplicationContext

    @Before
    fun setUp() {
        doReturn(mock<StartedProjectWorkTokenHandler>()).`when`(contextStub).getBean(StartedProjectWorkTokenHandler::class.java)
        doReturn(mock<WorklogDurationHandler>()).`when`(contextStub).getBean(WorklogDurationHandler::class.java)
        doReturn(mock<WorklogLocationHandler>()).`when`(contextStub).getBean(WorklogLocationHandler::class.java)
        doReturn(mock<WorklogMessageHandler>()).`when`(contextStub).getBean(WorklogMessageHandler::class.java)
        doReturn(mock<WorklogDescriptionHandler>()).`when`(contextStub).getBean(WorklogDescriptionHandler::class.java)
        doReturn(mock<WorklogStartEndHandler>()).`when`(contextStub).getBean(WorklogStartEndHandler::class.java)
        doReturn(mock<WorklogAuthHandler>()).`when`(contextStub).getBean(WorklogAuthHandler::class.java)
        doReturn(mock<WorklogHandler>()).`when`(contextStub).getBean(WorklogHandler::class.java)
        doReturn(mock<CollaboratorHandler>()).`when`(contextStub).getBean(CollaboratorHandler::class.java)
        doReturn(mock<CompanyHandler>()).`when`(contextStub).getBean(CompanyHandler::class.java)
        doReturn(mock<CountryHandler>()).`when`(contextStub).getBean(CountryHandler::class.java)
        doReturn(mock<CustomerHandler>()).`when`(contextStub).getBean(CustomerHandler::class.java)
        doReturn(mock<ProjectHandler>()).`when`(contextStub).getBean(ProjectHandler::class.java)
        doReturn(mock<UserHandler>()).`when`(contextStub).getBean(UserHandler::class.java)
    }

    @Test
    fun `Checks if is healthy`() {

        val routerFunction = Routes(contextStub).router()
        val webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build()
        val isHealthyResponse = webTestClient.get()
                .uri("/is-healthy")
                .exchange()
                .expectStatus()
                .isOk
                .expectBody(Boolean::class.java)
                .returnResult()

        assertTrue(isHealthyResponse.responseBody!!)
    }
}
