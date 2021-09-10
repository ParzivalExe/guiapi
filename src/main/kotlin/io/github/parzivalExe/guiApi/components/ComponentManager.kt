package io.github.parzivalExe.guiApi.components

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

    fun isItemComponent(item: ItemStack, itemSlot: Int): Boolean {
        return components.any { component -> component.meta.buildItem() == item && component.place == itemSlot }
    }

    fun getComponentFromItem(item: ItemStack, itemSlot: Int): Component? {
        return components.firstOrNull { component -> component.meta.buildItem() == item && component.place == itemSlot }
    }

    fun getAllComponents(): ArrayList<Component> {
        return components
    }

}