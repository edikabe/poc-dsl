package com.github.julglotain.pocdsl.dsl.framework

import com.autodsl.annotation.AutoDsl
import com.autodsl.annotation.AutoDslCollection

@AutoDsl("scenario")
data class ScenarioSpec(
    val name: String,
    @AutoDslCollection(concreteType = ArrayList::class)
    val steps: List<ScenarioStepSpec>?
)

interface ScenarioStepSpec {
    fun description(): String
    fun type(): String
}

@AutoDsl("request")
data class RequestStepSpec(
    @AutoDslCollection(concreteType = ArrayList::class)
    val headers: List<RequestHeaderSpec>?,
    val body: Any?,
    val bodyAsString: String?,
    val description: String?,
    val mapReponse: MapResponseSpec?
) : ScenarioStepSpec {
    override fun description(): String = this.description!!
    override fun type(): String = "RequestStepSpec"
}

@AutoDsl
data class AssertionStepSpec(
    val assertThat: (actual: String) -> Unit,
    val description: String?
) : ScenarioStepSpec {
    override fun description(): String = this.description!!
    override fun type(): String = "AssertionStepSpec"
}

interface AssertThatFunction {
    fun assert(actual: String): Unit
}

@AutoDsl("mapResponse")
data class MapResponseSpec(
    @AutoDslCollection(concreteType = ArrayList::class, inline = true)
    val fields: List<FieldMappingSpec>?
)

@AutoDsl("field")
data class FieldMappingSpec(
    val src: String,
    val dest: String?
)

@AutoDsl("header")
data class RequestHeaderSpec(
    val name: String,
    val value: String
)

annotation class ApiTarget(
    val value: String,
    val method: ApiTargetMethod = ApiTargetMethod.Get
)

enum class ApiTargetMethod {
    Get, Post, Put, Delete
}


/*

    req {

    } mapReponse


 */