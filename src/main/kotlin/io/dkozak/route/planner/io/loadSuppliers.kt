package io.dkozak.route.planner.io

import io.dkozak.route.planner.model.Supplier
import org.pcollections.PVector
import org.pcollections.TreePVector
import java.io.File

fun loadSuppliersFromFile(filename: String): PVector<Supplier> = File(filename).useLines {
    processCsv(it)
}

fun loadSuppliersFromResource(resource: String): PVector<Supplier> = ClassLoader.getSystemClassLoader().getResourceAsStream(resource).bufferedReader()
        .useLines { processCsv(it) }


private fun processCsv(lines: Sequence<String>) = TreePVector.from(lines
        .drop(1)
        .map { it.split(",").map(String::toInt) }
        .map { (id, x, y, prod) -> Supplier(id, x to y, prod) }
        .toList())

