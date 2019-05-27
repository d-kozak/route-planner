package io.dkozak.route.planner.model

data class Supplier(
        val id: Int,
        val pos: Location,
        val prod: Int
) {
    override fun toString(): String = "{$id}"
}