package de.kotlincook.vaadin

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.orderedlayout.VerticalLayout

open class ExtVertivalLayout: VerticalLayout() {

    val components: MutableList<Component> = mutableListOf()

    override fun add(vararg compsToAdd: Component) {
        super.add(*compsToAdd)
        this.components.addAll(compsToAdd)
    }

    fun insert(pos: Int, vararg compsToInsert: Component) {

        fun swapComponents(i: Int) {
            replace(components[i - 1], components[i])
            val temp = components[i - 1]
            components[i - 1] = components[i]
            components[i] = temp
        }

        require(pos in 0..components.size) {
            "Value of pos = $pos is not in '0..${components.size}'"
        }
        val prevComponentsSize = components.size
        add(*compsToInsert)
        for (i in prevComponentsSize downTo pos + 1) {
            for (j in i until i + compsToInsert.size) {
                swapComponents(j)
            }
        }
    }

}