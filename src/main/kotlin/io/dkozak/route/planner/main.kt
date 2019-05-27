package io.dkozak.route.planner

import io.dkozak.route.planner.io.loadSuppliers
import io.dkozak.route.planner.io.printPlan
import io.dkozak.route.planner.model.ModelConfiguration
import io.dkozak.route.planner.runtime.SimulationConfiguration
import io.dkozak.route.planner.runtime.planRoute
import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionLagrangeForm
import java.util.*


fun main() {
    val suppliers = loadSuppliers()

    val simulationConfiguration = SimulationConfiguration(10000)

    val results = mutableListOf<Pair<Double, Double>>()

    for (wantedUnits in 0..2000 step 50) {
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

    println(Arrays.toString(function.coefficients))


}

fun interpolateFunction(values: List<Pair<Double, Double>>): PolynomialFunctionLagrangeForm {
    val x = values.map { it.first }.map { it }.toDoubleArray()
    val y = values.map { it.second }.toDoubleArray()
    return PolynomialFunctionLagrangeForm(x, y)
}