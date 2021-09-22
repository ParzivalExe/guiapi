package io.github.parzivalExe.guiApi.components

import io.github.parzivalExe.guiApi.GuiManager
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class ComponentEvents : Listener{

    @EventHandler
    fun onClick(event: InventoryClickEvent) {
        if(clickIsRight(event) && event.whoClicked is Player && GuiManager.isInventoryGui(event.inventory)) {
            val gui = GuiManager.getGuiFromInventory(event.inventory)!!
            if(ComponentManager.isItemComponent(event.currentItem, event.slot, gui)) {
                val component = ComponentManager.getComponentFromItem(event.currentItem, event.slot, gui)!!

                component.componentClicked(event.whoClicked, gui, event.action, event.slot, event.click)
                component.startClickAction(event.whoClicked as Player, gui, event.action, event.click)

                event.isCancelled = true
            }
        }
    }

    private fun clickIsRight(event: InventoryClickEvent): Boolean = event.currentItem != null && event.slot == event.rawSlot


}