package io.dkozak.route.planner.runtime.modificatinos

import io.dkozak.route.planner.runtime.RoutePlan
import io.dkozak.route.planner.runtime.Truck
import kotlin.random.Random

var split = 0
var nonLocalRandom = 0
var localRandom = 0

fun RoutePlan.randomModification(): RoutePlan {
    val rnd = Random.nextDouble()
    return when {
        rnd < 0.3 -> this.nonLocalRandomModification()
        rnd < 0.5 -> this.splitTruckModification()
        else -> this.localRandomModification()
    }
}

fun RoutePlan.splitTruckModification(): RoutePlan {
    if (this.trucks.isEmpty())
        return this
    val (removedTruck, updatedPlan) = this.removeRandomTruck()

    if (removedTruck.suppliers.size < 2)
        return this

    split++
    val (a, b) = removedTruck.randomSplit()

    return updatedPlan
            .addTruck(a)
            .addTruck(b)
}


fun RoutePlan.removeRandomTruck(): Pair<Truck, RoutePlan> {
    val i = Random.nextInt(this.trucks.size)
    return this.trucks[i] to this.removeTruck(i)
}

fun RoutePlan.localRandomModification(): RoutePlan {
    if (this.trucks.isEmpty())
        return this

    localRandom++
    val i = Random.nextInt(this.trucks.size)
    val modifiedTruck = trucks[i].randomPathModification()
    return this.copy(
            trucks = trucks.with(i, modifiedTruck)
    )
}

fun RoutePlan.nonLocalRandomModification(): RoutePlan {
    if (this.trucks.size < 2)
        return this
    val fromIndex = Random.nextInt(this.trucks.size)
    if (trucks[fromIndex].suppliers.size < 2)
        return this

    val (movedSupplier, fromTruck) = trucks[fromIndex].removeRandomSupplier()

    for (i in 0 until trucks.size) {
        if (i != fromIndex) {
            if (trucks[i].isNotFull()) {
                nonLocalRandom++
                return this.copy(
                        trucks = trucks.with(fromIndex, fromTruck)
                                .with(i, trucks[i].addSupplier(movedSupplier))
                )
            }
        }
    }

    return this
}