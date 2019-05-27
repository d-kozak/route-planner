package io.dkozak.route.planner.model

import org.pcollections.PVector

data class Supplier(
        val pos: Location,
        val prod: Int
)

data class ModelConfiguration(
        val wantedResourceUnits: Int,
        val suppliers: PVector<Supplier>,
        val dcPos: Location = Pair(500, 500),
        val costPerKm: MoneyPerKm = MoneyPerKm(0.3),
        val truckCapacity: Int = 200,
        val costTruckPerDay: Money = Money(50.0)
)