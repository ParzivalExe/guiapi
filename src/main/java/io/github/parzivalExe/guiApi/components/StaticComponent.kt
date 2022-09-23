package io.github.parzivalExe.guiApi.components

import io.github.parzivalExe.guiApi.Gui
import io.github.parzivalExe.guiApi.events.StaticComponentClickedEvent
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.inventory.ItemStack

class StaticComponent(componentMeta: ComponentMeta) : Component(componentMeta) {

    @Deprecated("DON'T USE: This Constructor is only used for XML and shouldn't be used in Code itself", ReplaceWith("new StaticComponent(ComponentMeta)"))
    internal constructor() : this(ComponentMeta("", ItemStack(Material.WHITE_WOOL)))

    override fun componentClicked(whoClicked: HumanEntity, gui: Gui, action: InventoryAction, slot: Int, clickType: ClickType) {
        Bukkit.getPluginManager().callEvent(StaticComponentClickedEvent(this, whoClicked, gui, action, slot, clickType))
    }

}