package io.dkozak.route.planner.dsl

import io.dkozak.route.planner.io.loadSuppliersFromFile
import io.dkozak.route.planner.model.Location
import io.dkozak.route.planner.model.ModelConfiguration
import io.dkozak.route.planner.model.Money
import io.dkozak.route.planner.model.MoneyPerKm

data class ModelDsl(
        var wantedResourceUnits: Int = 0,
        var suppliersFile: String = "routes.csv",
        var dcPos: Location = Pair(500, 500),
        var costPerKm: Double = 0.3,
        var truckCapacity: Int = 200,
        var costTruckPerDay: Double = 50.0
)

fun model(init: ModelDsl.() -> Unit): ModelDsl = ModelDsl().apply(init)


fun ModelDsl.process(): ModelConfiguration {
    val suppliers = loadSuppliersFromFile(this.suppliersFile)
    return ModelConfiguration(wantedResourceUnits, suppliers, dcPos, MoneyPerKm(costPerKm), truckCapacity, Money(costTruckPerDay))
}