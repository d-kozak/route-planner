package io.dkozak.route.planner.io

import io.dkozak.route.planner.runtime.RoutePlan

fun printPlan(routePlan: RoutePlan) {
    println("====================ROUTE PLAN====================")
    println("# of Trucks: ${routePlan.trucks.size}")
    println("max units ${routePlan.maxPossibleUnits}")
    val suppliers = routePlan.trucks
            .mapIndexed { i, truck -> "\ttruck[$i] -> units: ${truck.maxPossibleUnits}, distance:${truck.totalDistance(routePlan.configuration.dcPos)}, supplierCount:${truck.suppliers.size} ->  ${truck.suppliers}" }
            .joinToString("\n")
    println(suppliers)
    println("Total distance: ${routePlan.totalDistance}")
    println("Total price: ${routePlan.price}")
    println("==================================================")
}