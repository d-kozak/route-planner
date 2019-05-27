package io.dkozak.route.planner.runtime

import io.dkozak.route.planner.model.ModelConfiguration
import io.dkozak.route.planner.model.Money

fun calculatePrice(plan: RoutePlan, configuration: ModelConfiguration): Money {
    val totalDistance = plan.totalDistance(configuration.dcPos)
    val travelPrice = configuration.costPerKm * totalDistance
    return travelPrice + configuration.costTruckPerDay * plan.trucks.size
}