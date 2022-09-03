package name.nkonev.dgs.issue.repro.dgs.issue.repro

import com.netflix.graphql.dgs.client.GraphQLResponse
import com.netflix.graphql.dgs.client.MonoGraphQLClient
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Answers
import org.mockito.kotlin.whenever
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.web.reactive.function.client.WebClient


@SpringBootTest(classes = [Application::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {

    @LocalServerPort
    private lateinit var port: Integer

    private lateinit var monoGraphQLClient: MonoGraphQLClient

    @MockBean(answer = Answers.CALLS_REAL_METHODS)
    private lateinit var mockedDataFetcher: CarsDataFetcher

    @BeforeEach
    fun be() {
        val webClient: WebClient = WebClient.create("http://localhost:$port/graphql")
        monoGraphQLClient = MonoGraphQLClient.createWithWebClient(webClient)
    }

    @Test
    fun shows() {
        val query = "{ car { id model }}"

        whenever(mockedDataFetcher.car()).thenReturn(Car("mocked_one", "Mocked BMW"))

        val response: GraphQLResponse = monoGraphQLClient.reactiveExecuteQuery(query).block()!!
        val id: String = response.extractValueAsObject("car.id", String::class.java)
        assertTrue(id == "mocked_one")
    }
}
