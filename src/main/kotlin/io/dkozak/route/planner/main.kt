package io.dkozak.route.planner

import io.dkozak.route.planner.io.loadSuppliers
import io.dkozak.route.planner.io.printPlan
import io.dkozak.route.planner.model.ModelConfiguration
import io.dkozak.route.planner.runtime.SimulationConfiguration
import io.dkozak.route.planner.runtime.planRoute


fun main() {
    val suppliers = loadSuppliers()
    val modelConfiguration = ModelConfiguration(500, suppliers)
    val simulationConfiguration = SimulationConfiguration(1000)

    println(modelConfiguration)

    val plan = planRoute(modelConfiguration, simulationConfiguration)
    printPlan(plan)
}