package io.github.parzivalExe.guiApi

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryCloseEvent

class GuiEvents : Listener{

    @EventHandler
    fun onCloseGui(event: InventoryCloseEvent) {
        if(GuiManager.isInventoryGui(event.inventory)) {
            val gui = GuiManager.getGuiFromInventory(event.inventory)!!
            gui.finalizeGui()
        }
    }

}