package io.dkozak.route.planner.model

inline class Money(val value: Double) {

    operator fun times(other: Money) = Money(this.value * other.value)
    operator fun times(scale: Int) = Money(this.value * scale)
    operator fun plus(other: Money) = Money(this.value + other.value)

    override fun toString(): String = "${value} e"
}