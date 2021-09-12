package io.github.parzivalExe.guiApi.components

import io.github.parzivalExe.guiApi.Gui
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLContent
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.inventory.ItemStack

class Folder(@XMLContent(necessary = true) var newOpenGui: Gui, meta: ComponentMeta) : Component(meta) {

    private var isNewGuiOpened = false

    @Suppress("unused")
    internal constructor() : this(Gui("FolderGui"), ComponentMeta("", ItemStack(Material.CHEST)))

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