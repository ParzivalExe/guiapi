package io.github.parzivalExe.guiApi.components

import io.github.parzivalExe.guiApi.Gui
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction

class Folder(var newOpenGui: Gui, meta: ComponentMeta) : Component(meta) {

    var isNewGuiOpened = false

    override fun finalizeComponent() {
        super.finalizeComponent()
        if(!isNewGuiOpened) {
            newOpenGui.finalizeGui()
        }
    }

    override fun componentClicked(whoClicked: HumanEntity, gui: Gui, action: InventoryAction, slot: Int, clickType: ClickType) {
        if(whoClicked is Player) {
            isNewGuiOpened = true
            newOpenGui.openGui(whoClicked)
        }
    }

}