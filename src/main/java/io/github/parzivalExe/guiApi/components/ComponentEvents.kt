package io.github.parzivalExe.guiApi.components

import io.github.parzivalExe.guiApi.GuiManager
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class ComponentEvents : Listener{

    @EventHandler
    fun onClick(event: InventoryClickEvent) {
        if(clickIsRight(event) && event.whoClicked is Player) {
            val component = ComponentManager.getComponentFromItem(event.currentItem, event.slot)!!
            val gui = GuiManager.getGuiFromInventory(event.inventory)!!

            component.componentClicked(event.whoClicked, gui, event.action, event.slot, event.click)
            component.startClickAction(event.whoClicked as Player, gui, event.action, event.click)

            event.isCancelled = true
        }
    }

    private fun clickIsRight(event: InventoryClickEvent): Boolean {
        return event.currentItem != null && event.slot == event.rawSlot
                && ComponentManager.isItemComponent(event.currentItem, event.slot) && GuiManager.isInventoryGui(event.inventory)
    }


}