package io.github.parzivalExe.guiApi

import io.github.parzivalExe.guiApi.components.ComponentManager
import org.bukkit.inventory.Inventory
import kotlin.random.Random

object GuiManager {

    private val guis = arrayListOf<Gui>()

    fun isInventoryGui(inventory: Inventory): Boolean {
        return guis.any { gui -> gui.inventory == inventory }
    }
    fun getGuiFromInventory(inventory: Inventory): Gui? {
        return guis.firstOrNull { gui -> gui.inventory == inventory }
    }

    fun initializeGui(gui: Gui): Int {
        synchronized(guis) {
            var id: Int
            do {
                id = Random.nextInt()
            } while (guis.any { gui -> gui.id == id })

            guis.add(gui)
            return id
        }
    }
    fun finalizeGui(gui: Gui): Boolean {
        synchronized(guis) {
            return@synchronized guis.remove(gui)
        }
        return false
    }

    fun getAllGuis(): ArrayList<Gui> {
        return guis
    }

}