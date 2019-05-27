package io.dkozak.route.planner.model

data class Supplier(
        val pos: Location,
        val prod: Int
)

data class Configuration(
        val wantedResourceUnits: Int,
        val suppliers: List<Supplier>,
        val dcPos: Location = Pair(500, 500),
        val costPerKm: MoneyPerKm = MoneyPerKm(0.3),
        val truckCapacity: Int = 200,
        val costTruckPerDay: Money = Money(50.0)
)