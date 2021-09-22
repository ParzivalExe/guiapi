package io.github.parzivalExe.guiApi.components

import io.github.parzivalExe.guiApi.Gui
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.inventory.ItemStack

class FreeSpaceComponent(meta: ComponentMeta) : Component(meta) {

    @Suppress("DEPRECATION", "unused")
    constructor() : this(ComponentMeta("", ItemStack(Material.BLACK_STAINED_GLASS_PANE)))

    override fun componentClicked(whoClicked: HumanEntity, gui: Gui, action: InventoryAction, slot: Int, clickType: ClickType) {

    }

}