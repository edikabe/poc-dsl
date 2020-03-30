package com.github.julglotain.pocdsl.dsl

import com.autodsl.annotation.AutoDsl
import com.autodsl.annotation.AutoDslCollection

/**
 * Prépare une recherche de solution.
 *
 */
@AutoDsl
data class Search(
    val id: String,
    @AutoDslCollection(concreteType = ArrayList::class, inline = true)
    val passengers: List<Passenger>?
)

/**
 * Ajout d'un passager.
 */
@AutoDsl
data class Passenger(
    val age: Int,
    val name: String,
    val email: String
)