package io.dkozak.route.planner.runtime

import io.dkozak.route.planner.model.Configuration
import io.dkozak.route.planner.model.distance

fun planRoute(configuration: Configuration): RoutePlan {
    val plan = RoutePlan()
    val freeSuppliers = configuration.suppliers.toMutableList()
    while (plan.maxPossibleUnits < configuration.wantedResourceUnits) {
        val newTruckNeeded = plan.trucks.lastOrNull()?.maxPossibleUnits ?: Int.MAX_VALUE >= configuration.truckCapacity
        val truck = if (!newTruckNeeded) plan.trucks.last() else Truck(configuration.truckCapacity)
        val startPoint = truck.suppliers.lastOrNull()?.pos ?: configuration.dcPos

        val nearest = freeSuppliers
                .minBy { startPoint.distance(it.pos) } ?: throw NoFeasiblePlanException("More suppliers needed")
        truck.suppliers.add(nearest)
        freeSuppliers.remove(nearest)
        if (newTruckNeeded)
            plan.trucks.add(truck)
    }
    return plan
}