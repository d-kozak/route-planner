package io.dkozak.route.planner.runtime.modificatinos

import io.dkozak.route.planner.model.Supplier
import io.dkozak.route.planner.runtime.Truck
import kotlin.random.Random

fun Truck.randomPathModification(): Truck {
    if (suppliers.size < 2) return this
    val i = Random.nextInt(suppliers.size)
    val j = Random.nextInt(suppliers.size)
    val supplierI = suppliers[i]
    val supplierJ = suppliers[j]
    return this.copy(
            suppliers = suppliers.with(i, supplierJ)
                    .with(j, supplierI)
    )
}

fun Truck.removeRandomSupplier(): Pair<Supplier, Truck> {
    val i = Random.nextInt(suppliers.size)
    return suppliers[i] to removeSupplier(i)
}

fun Truck.randomSplit(): Pair<Truck, Truck> {
    val i = if (this.suppliers.size == 2) 1 else Random.nextInt(1, suppliers.size - 1)
    val left = this.suppliers.subList(0, i)
    val right = this.suppliers.subList(i, this.suppliers.size)

    require(left.size > 0) { "left is empty for ${this}" }
    require(right.size > 0) { "right is empty for ${this}" }

    return this.copy(suppliers = left) to this.copy(suppliers = right)
}