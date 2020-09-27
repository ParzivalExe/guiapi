package io.github.parzivalExe.guiApi.components

import io.github.parzivalExe.guiApi.Gui
import io.github.parzivalExe.guiApi.events.MessageComponentClickedEvent
import org.bukkit.Bukkit
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction

class MessageComponent(meta: ComponentMeta) : Component(meta) {

    var message: String? = null

    override fun componentClicked(whoClicked: HumanEntity, gui: Gui, action: InventoryAction, slot: Int, clickType: ClickType) {
        if(whoClicked is Player && message != null) {
            whoClicked.sendMessage(message)
            gui.closeGui()
            Bukkit.getPluginManager().callEvent(MessageComponentClickedEvent(this, whoClicked, gui, action, slot, clickType))
        }
    }

}