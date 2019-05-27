package io.dkozak.route.planner.io

import io.dkozak.route.planner.model.Supplier
import kotlin.streams.toList

fun loadSuppliers(): List<Supplier> {
    ClassLoader.getSystemClassLoader().getResourceAsStream("routes.csv").use { stream ->
        return stream.bufferedReader().lines()
                .skip(1)
                .map { it.split(",").map(String::toInt) }
                .map { (_, x, y, prod) -> Supplier(x to y, prod) }
                .toList()
    }
}