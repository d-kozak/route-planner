package io.dkozak.route.planner.runtime

import io.dkozak.route.planner.io.printPlan
import io.dkozak.route.planner.model.ModelConfiguration
import io.dkozak.route.planner.model.distance

fun planRoute(modelConfiguration: ModelConfiguration, simulationConfiguration: SimulationConfiguration): RoutePlan {
    var bestPlan = findAnyPlan(modelConfiguration)

    var current = bestPlan
    val all = mutableSetOf<RoutePlan>()
    for (i in 1..simulationConfiguration.iterations) {
        val plan = current.localRandomModification()
        println("Step $i")
        printPlan(plan)

        if (plan < bestPlan)
            bestPlan = plan
        all.add(plan)
        current = plan
    }

    println("Explored ${all.size} distinct plans")
    return bestPlan
}

private fun findAnyPlan(configuration: ModelConfiguration): RoutePlan {
    var plan = RoutePlan(configuration)
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