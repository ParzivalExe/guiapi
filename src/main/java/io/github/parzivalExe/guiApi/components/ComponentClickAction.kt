package io.github.parzivalExe.guiApi.components

import io.github.parzivalExe.guiApi.Gui
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction

interface ComponentClickAction {

    fun onClick(component: Component, whoClicked: Player, gui: Gui, action: InventoryAction, clickType: ClickType): Boolean

}