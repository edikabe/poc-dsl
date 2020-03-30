package com.github.julglotain.pocdsl.app

import com.github.julglotain.pocdsl.dsl.api.sales.searchSolutions
import com.github.julglotain.pocdsl.dsl.framework.*
import com.github.julglotain.pocdsl.dsl.passenger
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlin.properties.Delegates

val gson: Gson = GsonBuilder().setPrettyPrinting().create()

fun profile(profile: String): RequestHeaderSpec = RequestHeaderSpec("X-Channel-Profile", profile)

class ScenarioRunner {

    private val scenariosList: MutableList<ScenarioSpec> = mutableListOf()

    private var currentScenarioHolder: ScenarioSpec by Delegates.notNull()

    fun register(scenario: ScenarioSpec): Unit {
        scenariosList.add(scenario);
    }

    fun run() {
        if (scenariosList.isEmpty()) {
            println("No scenario to run")
        }
        scenariosList.forEachIndexed { index, scenarioSpec ->
            println("Scenario #${index + 1}: steps: ${scenarioSpec.steps?.size}")
            scenarioSpec.steps?.forEach{ stepSpec ->
                println(" - ${stepSpec.type()}, desc: ${stepSpec.description()}")
            }
        }
    }

}

fun main() {

    val runner = ScenarioRunner()

    val example =
        scenario {
            name = "Example scenario, with requests chaining"
            steps {

                +request {
                    description = "First step is a call api to /searchSolution"
                    headers {
                        +profile("CHANNEL_789234")
                    }
                    body = searchSolutions {
                        id = "plop"
                        +passenger {
                            name = "bob the sponge"
                            age = 25
                            email = "bobthesponge@nickolodeon.com"
                        }
                        origin = "NANTES"
                    }
                    mapResponse {
                        +field {
                            src = "plop"
                        }
                    }
                }

                +assertionStepSpec {
                    description = "Assert that previous request is OK"
                    assertThat = fun(actual: String) {
                        println(actual)
                    }
                }
            }
        }

    runner.register(example)

    runner.run()

    println(gson.toJson(example))

}