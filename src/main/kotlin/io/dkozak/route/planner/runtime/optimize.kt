package io.dkozak.route.planner.runtime

import io.dkozak.route.planner.io.printPlan
import io.dkozak.route.planner.model.ModelConfiguration
import io.dkozak.route.planner.model.distance
import io.dkozak.route.planner.runtime.modificatinos.localRandom
import io.dkozak.route.planner.runtime.modificatinos.nonLocalRandom
import io.dkozak.route.planner.runtime.modificatinos.randomModification
import io.dkozak.route.planner.runtime.modificatinos.split
import kotlin.random.Random


fun planRoute(modelConfiguration: ModelConfiguration, simulationConfiguration: SimulationConfiguration): RoutePlan {
    var best = findAnyPlan(modelConfiguration)
    var current = best
    val all = mutableSetOf(current)
    for (i in 1..simulationConfiguration.iterations) {
        if (current < best)
            best = current
        val next = current.randomModification()
        println("Step $i -> ${current.price}")
        all.add(next)

        if (Random.nextDouble() < 0.1 || next < current) {
            current = next
        }

        // if we are too far off, continue with current best solution
        if (current.price > best.price * 2)
            current = best
    }

    println("Explored ${all.size} distinct plans")
    println("Local random $localRandom")
    println("Nonlocal random $nonLocalRandom")
    println("Split truck $split")

    for (solution in all.sorted().take(10)) {
        printPlan(solution)
    }

    return best
}

fun findAnyPlan(configuration: ModelConfiguration): RoutePlan {
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