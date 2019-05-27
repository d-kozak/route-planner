package io.dkozak.route.planner.runtime

import io.dkozak.route.planner.model.Configuration
import io.dkozak.route.planner.model.distance

fun planRoute(configuration: Configuration): RoutePlan {
    var plan = RoutePlan()
    val freeSuppliers = configuration.suppliers.toMutableList()
    while (plan.maxPossibleUnits < configuration.wantedResourceUnits) {
        val newTruckNeeded = plan.trucks.lastOrNull()?.maxPossibleUnits ?: Int.MAX_VALUE >= configuration.truckCapacity
        val truck = if (!newTruckNeeded) plan.trucks.last() else Truck(configuration.truckCapacity)
        val startPoint = truck.suppliers.lastOrNull()?.pos ?: configuration.dcPos

        val nearest = freeSuppliers
                .minBy { startPoint.distance(it.pos) } ?: throw NoFeasiblePlanException("More suppliers needed")
        val modifiedTruck = truck.addSupplier(nearest)

        freeSuppliers.remove(nearest)
        plan = if (newTruckNeeded)
            plan.addTruck(modifiedTruck)
        else
            plan.replaceLastTruck(modifiedTruck)
    }
    return plan
}