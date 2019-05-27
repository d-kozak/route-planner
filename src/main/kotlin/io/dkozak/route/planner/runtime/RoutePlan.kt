package io.dkozak.route.planner.runtime

import io.dkozak.route.planner.model.DistanceInKm
import io.dkozak.route.planner.model.Location
import io.dkozak.route.planner.model.Supplier
import io.dkozak.route.planner.model.distance


data class RoutePlan(
        val trucks: MutableList<Truck> = mutableListOf()
) {
    val maxPossibleUnits
        get() = trucks.map {
            it.maxPossibleUnits
        }.sum()

    val usedSuppliers
        get() = trucks.asSequence()
                .flatMap { it.suppliers.asSequence() }

    fun totalDistance(dcPos: Location) = trucks.map { it.totalDistance(dcPos) }.reduce(DistanceInKm::plus)
}

data class Truck(
        val suppliers: MutableList<Supplier> = mutableListOf()
) {
    val maxPossibleUnits
        get() = suppliers.map { it.prod }.sum()

    fun totalDistance(dcPos: Location): DistanceInKm = when (suppliers.size) {
        0 -> DistanceInKm(0.0)
        1 -> dcPos.distance(suppliers[0].pos) * 2
        else -> suppliers.mapIndexed { i, supplier ->
            when (i) {
                0 -> dcPos.distance(supplier.pos)
                suppliers.size - 1 -> suppliers[i - 1].pos.distance(supplier.pos) + supplier.pos.distance(dcPos)
                else -> suppliers[i - 1].pos.distance(supplier.pos)
            }
        }.reduce(DistanceInKm::plus)
    }
}