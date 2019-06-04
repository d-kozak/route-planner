package io.dkozak.route.planner.model

import org.pcollections.PVector

data class ModelConfiguration(
        val wantedResourceUnits: Int,
        val suppliers: PVector<Supplier>,
        val dcPos: Location = Pair(500, 500),
        val costPerKm: MoneyPerKm = MoneyPerKm(0.2),
        val truckCapacity: Int = 200,
        val costTruckPerDay: Money = Money(50.0)
)