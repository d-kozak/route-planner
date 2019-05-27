package io.dkozak.route.planner.io

import io.dkozak.route.planner.model.Supplier
import org.pcollections.PVector
import org.pcollections.TreePVector
import kotlin.streams.toList

fun loadSuppliers(): PVector<Supplier> {
    ClassLoader.getSystemClassLoader().getResourceAsStream("routes.csv").use { stream ->
        return TreePVector.from(stream.bufferedReader().lines()
                .skip(1)
                .map { it.split(",").map(String::toInt) }
                .map { (_, x, y, prod) -> Supplier(x to y, prod) }
                .toList())
    }
}