package io.dkozak.route.planner.runtime.utils

import io.dkozak.route.planner.model.Supplier
import io.dkozak.route.planner.runtime.RoutePlan
import io.dkozak.route.planner.runtime.Truck
import org.pcollections.PVector
import org.pcollections.TreePVector

fun RoutePlan.diff(other: RoutePlan): PVector<PVector<String>> {
    var trucks = TreePVector.empty<PVector<String>>()

    for (i in 0 until Math.max(this.trucks.size, other.trucks.size)) {
        if (i < this.trucks.size && i < other.trucks.size) {
            trucks = trucks.plus(this.trucks[i].diff(other.trucks[i]))
        } else if (i < this.trucks.size) {
            trucks = trucks.plus(TreePVector.empty<String>().plus("2"))
        } else if (i < other.trucks.size) {
            trucks = trucks.plus(TreePVector.empty<String>().plus("2"))
        }
    }
    return trucks
}

fun Truck.diff(other: Truck, i: Int = 0, suppliers: PVector<String> = TreePVector.empty()): PVector<String> = when {
    i < this.suppliers.size && i < other.suppliers.size ->
        this.diff(other, i + 1, suppliers.plus(this.suppliers[i].diff(other.suppliers[i])))
    i < this.suppliers.size ->
        this.diff(other, i + 1, suppliers.plus("1"))
    i < other.suppliers.size ->
        this.diff(other, i + 1, suppliers.plus("1"))
    else -> suppliers
}

fun Supplier.diff(other: Supplier) = if (this == other) "1" else "0"