package io.github.parzivalExe.guiApi.components

import io.github.parzivalExe.guiApi.Gui
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLAttribute
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.inventory.ItemStack

class FreeSpaceComponent(meta: ComponentMeta) : Component(meta) {

    @XMLAttribute
    var forceNoFill = false

    @Suppress("DEPRECATION", "unused")
    @Deprecated("DON'T USE: This Constructor is only used for XML and shouldn't be used in Code itself", ReplaceWith("new FreeSpaceComponent(ComponentMeta)"))
    constructor() : this(ComponentMeta("", ItemStack(Material.STAINED_GLASS_PANE, 1, 7)))

    override fun componentClicked(whoClicked: HumanEntity, gui: Gui, action: InventoryAction, slot: Int, clickType: ClickType) {

    }

}