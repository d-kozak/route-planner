package io.dkozak.route.planner

import io.dkozak.route.planner.io.loadSuppliers
import io.dkozak.route.planner.io.printPlan
import io.dkozak.route.planner.model.Configuration
import io.dkozak.route.planner.runtime.planRoute


fun main() {
    val suppliers = loadSuppliers()
    val configuration = Configuration(500, suppliers)
    println(configuration)

    val plan = planRoute(configuration)
    printPlan(plan, configuration)
}