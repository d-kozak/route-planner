package io.dkozak.route.planner.runtime

import io.dkozak.route.planner.model.*
import org.pcollections.PVector
import org.pcollections.TreePVector


data class RoutePlan(
        val configuration: ModelConfiguration,
        val trucks: PVector<Truck> = TreePVector.empty()
) : Comparable<RoutePlan> {

    val maxPossibleUnits
        get() = trucks.map {
            it.maxPossibleUnits
        }.sum()

    val totalDistance
        get() = trucks.map { it.totalDistance(configuration.dcPos) }.reduce(DistanceInKm::plus)

    val price
        get() = configuration.costPerKm * totalDistance + configuration.costTruckPerDay * trucks.size

    fun addTruck(newTruck: Truck): RoutePlan = this.copy(trucks = trucks.plus(newTruck))

    fun replaceLastTruck(newTruck: Truck): RoutePlan = this.copy(trucks = trucks.with(trucks.size - 1, newTruck))


    fun localRandomModification(): RoutePlan {
        return this
    }

    override fun compareTo(other: RoutePlan): Int = this.price.compareTo(other.price)

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