import io.dkozak.route.planner.dsl.model


model {
    wantedResourceUnits = 500
    suppliersFile = "./src/main/resources/routes.csv"
    dcPos = 500 to 500
    costPerKm = 0.2
    truckCapacity = 200
    costTruckPerDay = 50.0
}