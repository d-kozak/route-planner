package io.dkozak.route.planner.model

typealias Location = Pair<Int, Int>

val Location.x
    get() = this.first
val Location.y
    get() = this.second


fun Location.distance(other: Location): DistanceInKm = DistanceInKm(euclideanDistance(this, other))


fun euclideanDistance(a: Location, b: Location) = (Math.sqrt(
        (Math.pow((a.x - b.x).toDouble(), 2.0) +
                Math.pow((a.y - b.y).toDouble(), 2.0)))
        )
