# Route planner

Route planner algorithm for Simulation and Optimization course. It tries to find at least suboptimal solution for the problem of choosing routes to collect given amount of resources from a set of producers with the aim of minimizing the transportation cost.

In the end it interpolates a function predicting the cost of transportation based on the amount of wanted resources.

## Build
```
gradle jar
```

## Execute
```
java -jar build/libs/route-planner-0.0.1-SNAPSHOT.jar
```

## Author
* d-kozak