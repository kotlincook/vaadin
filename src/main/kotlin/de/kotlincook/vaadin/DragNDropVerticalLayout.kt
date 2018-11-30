package de.kotlincook.vaadin

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.orderedlayout.VerticalLayout

abstract class DragNDropVerticalLayout : VerticalLayout() {

    protected val essentialComps: MutableList<Component> = mutableListOf()

    init {
        implantDropAreas()
    }

    fun append(vararg compsToAdd: Component) {
        validateNotToBeAnEssentialComp(compsToAdd)
        essentialComps.addAll(compsToAdd)
        implantDropAreas()
    }

    fun insert(beforeMe: Component, vararg compsToAdd: Component) {
        validateNotToBeAnEssentialComp(compsToAdd)
        val component = findEssentialCompAfter(beforeMe)
        if (component == null) {
            essentialComps.addAll(compsToAdd)
        }
        else {
            val index = essentialComps.indexOf(component)
            essentialComps.addAll(index, compsToAdd.asList())
        }
        implantDropAreas()
    }

    fun move(beforeMe: Component, compToMove: Component) {
        validateToBeEssentialComp(compToMove)
        delete(compToMove)
        insert(beforeMe, compToMove)
    }

    fun delete(compToDelete: Component) {
        validateToBeEssentialComp(compToDelete)
        essentialComps.remove(compToDelete)
        remove(compToDelete)
    }

    private fun validateNotToBeAnEssentialComp(comps: Array<out Component>) {
        comps.forEach {
            require(it !in essentialComps) {
                "$it cannot be added twice."
            }
        }
    }

    private fun validateToBeEssentialComp(comp: Component) {
        require(comp in essentialComps) {
            "$comp must be a member of the essential components"
        }
    }

    /**
     *
     * @param component must be
     */
    private fun findEssentialCompAfter(component: Component): Component? {
        var found = false
        for (comp in children) {
            if (comp == component) found = true
            if (found && comp in essentialComps) return comp
        }
        return null
    }


    protected fun implantDropAreas() {
        removeAll()
        super.add(createDropArea())
        for (comp in essentialComps) {
            super.add(comp)
            super.add(createDropArea())
        }
    }

    abstract fun createDropArea(): Component

}