package io.github.parzivalExe.guiApi.components

import io.github.parzivalExe.guiApi.Gui
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLAttribute
import io.github.parzivalExe.guiApi.events.MessageComponentClickedEvent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.inventory.ItemStack

class MessageComponent(meta: ComponentMeta, @XMLAttribute var message: String = "") : Component(meta) {

    @XMLAttribute
    var colorCodeChar = '&'

    @Suppress("unused")
    internal constructor() : this(ComponentMeta("", ItemStack(Material.WHITE_WOOL)))
    constructor(meta: ComponentMeta) : this(meta, "")


    override fun componentClicked(whoClicked: HumanEntity, gui: Gui, action: InventoryAction, slot: Int, clickType: ClickType) {
        if(whoClicked is Player) {
            whoClicked.sendMessage(ChatColor.translateAlternateColorCodes(colorCodeChar, message))
            gui.closeGui()
            Bukkit.getPluginManager().callEvent(MessageComponentClickedEvent(this, whoClicked, gui, action, slot, clickType, message))
        }
    }

}