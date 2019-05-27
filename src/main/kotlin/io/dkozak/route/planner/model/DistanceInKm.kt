package io.dkozak.route.planner.model

inline class DistanceInKm(val value: Double) : Comparable<DistanceInKm> {
    override fun compareTo(other: DistanceInKm): Int = this.value.compareTo(other.value)

    operator fun plus(other: DistanceInKm) = DistanceInKm(this.value + other.value)

    operator fun times(scale: Int) = DistanceInKm(this.value * scale)
}