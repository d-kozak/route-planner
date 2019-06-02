package io.dkozak.route.planner.runtime

import io.dkozak.route.planner.io.loadSuppliersFromFile
import io.dkozak.route.planner.model.ModelConfiguration
import io.dkozak.route.planner.model.Supplier
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.pcollections.TreePVector


class RoutePlanTest {

    @Test
    fun `equals test`() {
        val suppliers = loadSuppliersFromFile()
        val modelConfiguration = ModelConfiguration(1111, suppliers)
        val plan = findAnyPlan(modelConfiguration)

        assertThat(plan).isEqualTo(plan)
        assertThat(plan.removeTruck(0)).isNotEqualTo(plan)

        val truck = plan.trucks[0]
        assertThat(truck).isEqualTo(truck)
        assertThat(truck.addSupplier(Supplier(1, 10 to 20, 5))).isNotEqualTo(truck)
        assertThat(truck.removeSupplier(1)).isNotEqualTo(truck)
    }

    @Test
    fun `pcollection equals test`() {
        val vector = TreePVector.empty<Int>()
        assertThat(vector).isEqualTo(vector)
        assertThat(vector.plus(1)).isNotEqualTo(vector)
        assertThat(vector.plus(1)).isEqualTo(vector.plus(1))
    }
}