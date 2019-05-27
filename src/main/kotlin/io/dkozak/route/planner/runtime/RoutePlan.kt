package io.dkozak.route.planner.runtime

import io.dkozak.route.planner.model.DistanceInKm
import io.dkozak.route.planner.model.Location
import io.dkozak.route.planner.model.Supplier
import io.dkozak.route.planner.model.distance
import org.pcollections.PVector
import org.pcollections.TreePVector


data class RoutePlan(
        val trucks: PVector<Truck> = TreePVector.empty()
) {
    val maxPossibleUnits
        get() = trucks.map {
            it.maxPossibleUnits
        }.sum()

    fun totalDistance(dcPos: Location) = trucks.map { it.totalDistance(dcPos) }.reduce(DistanceInKm::plus)
    fun addTruck(newTruck: Truck): RoutePlan = this.copy(trucks = trucks.plus(newTruck))
    fun replaceLastTruck(newTruck: Truck): RoutePlan = this.copy(trucks = trucks.with(trucks.size - 1, newTruck))
}

data class Truck(
        val maxCapacity: Int,
        val suppliers: PVector<Supplier> = TreePVector.empty()
) {
    val maxPossibleUnits
        get() = Math.min(suppliers.map { it.prod }.sum(), maxCapacity)

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

    fun addSupplier(newSupplier: Supplier): Truck = this.copy(
            suppliers = suppliers.plus(newSupplier)
    )
}