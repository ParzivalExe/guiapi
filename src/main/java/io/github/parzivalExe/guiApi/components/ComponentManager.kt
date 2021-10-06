package io.github.parzivalExe.guiApi.components

import io.github.parzivalExe.guiApi.Gui
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

object ComponentManager {

    private val components = arrayListOf<Component>()

    fun getNewComponentId(): Int {
        var id: Int
        synchronized(components) {
            do {
                id = Random.nextInt()
            } while (components.any { component -> component.id == id })
        }
        return id
    }

    fun initializeComponent(component: Component) {
        synchronized(components) {
            components.add(component)
        }
    }

    fun finalizeComponent(component: Component): Boolean {
        synchronized(components) {
            return@synchronized components.remove(component)
        }
        return false
    }

    fun isItemComponent(item: ItemStack, itemSlot: Int, gui: Gui): Boolean {
        return components.any { component -> component.getGuiItem() == item && gui.hasComponent(component)
                && gui.getPositionOfComponent(component) == itemSlot }
    }

    fun getComponentFromItem(item: ItemStack, itemSlot: Int, gui: Gui): Component? {
        return components.firstOrNull { component -> component.getGuiItem() == item && gui.hasComponent(component)
                && gui.getPositionOfComponent(component) == itemSlot }
    }

    fun getAllComponents(): ArrayList<Component> {
        return components
    }

}