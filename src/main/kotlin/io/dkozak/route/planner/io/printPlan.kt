package io.dkozak.route.planner.io

import io.dkozak.route.planner.model.Configuration
import io.dkozak.route.planner.runtime.RoutePlan
import io.dkozak.route.planner.runtime.calculatePrice

fun printPlan(routePlan: RoutePlan, configuration: Configuration) {

    println("====================ROUTE PLAN====================")
    println("# of Trucks: ${routePlan.trucks.size}")
    println("max units ${routePlan.maxPossibleUnits}")
    val suppliers = routePlan.trucks
            .mapIndexed { i, truck -> "\ttruck[$i] -> units: ${truck.maxPossibleUnits}, distance:${truck.totalDistance(configuration.dcPos)}, supplierCount:${truck.suppliers.size} ->  ${truck.suppliers}" }
            .joinToString("\n")
    println(suppliers)
    println("Total distance: ${routePlan.totalDistance(configuration.dcPos)}")
    println("Total price: ${calculatePrice(routePlan, configuration)}")
    println("==================================================")
}