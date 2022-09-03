package name.nkonev.dgs.issue.repro.dgs.issue.repro

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery

data class Car(val id: String, val model: String)

@DgsComponent
class CarsDataFetcher {

    @DgsQuery
    fun car() : Car {
        return Car("one", "The real BMW")
    }

}