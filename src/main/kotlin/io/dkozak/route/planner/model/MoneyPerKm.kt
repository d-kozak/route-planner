package io.dkozak.route.planner.model

inline class MoneyPerKm(val value: Double) : Comparable<MoneyPerKm> {
    operator fun times(distance: DistanceInKm) = Money(this.value * distance.value)

    override fun compareTo(other: MoneyPerKm): Int = this.value.compareTo(other.value)

    override fun toString(): String = "${value} e/km"
}