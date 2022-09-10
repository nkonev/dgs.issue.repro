package name.nkonev.dgs.issue.repro.dgs.issue.repro

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.DgsSubscription
import org.reactivestreams.Publisher
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import reactor.core.publisher.Flux
import reactor.core.publisher.SynchronousSink
import java.security.Principal
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.concurrent.atomic.AtomicInteger

data class Car(val id: String, val model: String)

@DgsComponent
class CarsDataFetcher {

    @DgsQuery
    fun car() : Car {
        return Car("one", "The real BMW")
    }

    @DgsSubscription
    fun cars(): Publisher<Car> {
        val context: SecurityContext = SecurityContextHolder.getContext()
        val counter = AtomicInteger()
        val flux: Flux<Car> = Flux.generate { synchronousSink: SynchronousSink<Car> ->
            synchronousSink.next(
                Car(
                    ""+counter.getAndIncrement(),
                    "Top Secret Car " + org.apache.commons.lang3.RandomStringUtils.randomAscii(
                        3,
                        4
                    ) + " authentication from context is " + context.getAuthentication(),
                )
            )
        }
        return flux.delayElements(Duration.of(1, ChronoUnit.SECONDS))
    }
}