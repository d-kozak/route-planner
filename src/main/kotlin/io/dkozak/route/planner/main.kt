package io.dkozak.route.planner

import de.swirtz.ktsrunner.objectloader.KtsObjectLoader
import io.dkozak.route.planner.dsl.ModelDsl
import io.dkozak.route.planner.dsl.process
import io.dkozak.route.planner.io.loadSuppliersFromResource
import io.dkozak.route.planner.io.printPlan
import io.dkozak.route.planner.model.ModelConfiguration
import io.dkozak.route.planner.runtime.SimulationConfiguration
import io.dkozak.route.planner.runtime.planRoute
import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionLagrangeForm
import java.io.File
import java.util.*


fun main(args: Array<String>) {
    if (args.isNotEmpty()) {
        if (args.size != 1) throw IllegalArgumentException("Only one argument expected, the path to the config file")

        val configFile = args[0]
        executeWithConfiguration(configFile)
    } else {
        functionInterpolation()
    }
}

private fun executeWithConfiguration(configFile: String) {
    println("Reading configuration from file $configFile")
    val script = File(configFile).readText()
    val dsl = KtsObjectLoader().load<ModelDsl>(script)
    println("Finished, configuration is:")
    println(dsl)
    val modelConfig = dsl.process()
    val simulationConfig = SimulationConfiguration(1000, true)
    val plan = planRoute(modelConfig, simulationConfig)
    printPlan(plan)
}

private fun functionInterpolation() {
    val suppliers = loadSuppliersFromResource("routes.csv")

    val simulationConfiguration = SimulationConfiguration(10000)

    val results = mutableListOf<Pair<Double, Double>>()

    for (wantedUnits in 0..2000 step 50) {
        println("Executing for units $wantedUnits")
        val modelConfiguration = ModelConfiguration(wantedUnits, suppliers)
        val plan = planRoute(modelConfiguration, simulationConfiguration)
        printPlan(plan)
        results.add(wantedUnits.toDouble() to plan.price.value)
    }

    val function = interpolateFunction(results)

    for ((x, y) in results) {
        val dist = Math.abs(function.value(x) - y)
        require(dist < 50) { "For input $x the difference between real and interpolated is $dist, which is more than 50" }
    }

    println("Lagrange coefficients of interpolated function:")
    println(Arrays.toString(function.coefficients))
}

fun interpolateFunction(values: List<Pair<Double, Double>>): PolynomialFunctionLagrangeForm {
    val x = values.map { it.first }.map { it }.toDoubleArray()
    val y = values.map { it.second }.toDoubleArray()
    return PolynomialFunctionLagrangeForm(x, y)
}