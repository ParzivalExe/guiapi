package io.github.parzivalExe.guiApi.components

import io.github.parzivalExe.guiApi.Gui
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLContent
import io.github.parzivalExe.guiApi.events.FolderOpenedEvent
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.inventory.ItemStack

class Folder(@XMLContent(necessary = true) var newOpenGui: Gui?, meta: ComponentMeta) : Component(meta) {

    private var isNewGuiOpened = false

    @Suppress("unused")
    internal constructor() : this(null, ComponentMeta("", ItemStack(Material.CHEST)))

    override fun finalizeComponent() {
        super.finalizeComponent()
        if(!isNewGuiOpened) {
            newOpenGui?.finalizeGui()
        }
    }

    override fun componentClicked(whoClicked: HumanEntity, gui: Gui, action: InventoryAction, slot: Int, clickType: ClickType) {
        if(whoClicked is Player) {
            if(newOpenGui != null) {
                isNewGuiOpened = true
                newOpenGui?.openGui(whoClicked)
                Bukkit.getPluginManager().callEvent(FolderOpenedEvent(this, whoClicked, gui, action, place, clickType, newOpenGui!!))
            }
        }
    }

}