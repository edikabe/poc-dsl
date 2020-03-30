package com.github.julglotain.pocdsl.dsl.api.sales

import com.autodsl.annotation.AutoDsl
import com.autodsl.annotation.AutoDslCollection
import com.github.julglotain.pocdsl.dsl.Passenger
import com.github.julglotain.pocdsl.dsl.framework.ApiTarget

@AutoDsl
@ApiTarget("/searchSolutions")
data class SearchSolutions(
    val id: String,
    @AutoDslCollection(concreteType = ArrayList::class, inline = true)
    val passengers: List<Passenger>?,
    val origin: String,
    val destination: String?
)