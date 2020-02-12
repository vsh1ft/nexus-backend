package lt.boldadmin.nexus.backend.test.unit.factory

import com.nhaarman.mockitokotlin2.*
import lt.boldadmin.nexus.api.service.collaborator.CollaboratorUpdateSubscriber
import lt.boldadmin.nexus.backend.factory.CollaboratorUpdateSubscriberFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.context.support.GenericApplicationContext

class CollaboratorUpdateSubscriberFactoryTest {

    @Test
    @Suppress("RemoveExplicitTypeArguments")
    fun `Provides Collaborator update subscribers map`() {
        val contextStub: GenericApplicationContext = mock()
        val workWeekUpdateDummy: CollaboratorUpdateSubscriber = mock()
        val expectedSubscribersMap = mapOf("workWeek" to workWeekUpdateDummy)
        doReturn(workWeekUpdateDummy).`when`(contextStub)
            .getBean(any<String>(), eq(CollaboratorUpdateSubscriber::class.java))

        val actualSubscribersMap = CollaboratorUpdateSubscriberFactory(contextStub).create().invoke()

        assertEquals(expectedSubscribersMap, actualSubscribersMap)
    }
}
