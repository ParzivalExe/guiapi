package io.github.parzivalExe.guiApi.components

import io.github.parzivalExe.guiApi.Gui
import io.github.parzivalExe.guiApi.antlr.converter.TeleportLocationConverter
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLAttribute
import io.github.parzivalExe.guiApi.objects.TeleportLocation
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.inventory.ItemStack

class TeleportComponent(componentMeta: ComponentMeta, @XMLAttribute(attrName = "location", converter = TeleportLocationConverter::class)
                            var finalLocation: TeleportLocation) : Component(componentMeta) {

    constructor(componentMeta: ComponentMeta) : this(componentMeta, TeleportLocation())

    @Suppress("unused")
    @Deprecated("DON'T USE: This Constructor is only used for XML and shouldn't be used in Code itself", ReplaceWith("new TeleportComponent(ComponentMeta)"))
    constructor() : this(ComponentMeta("", ItemStack(Material.WOOL)))

    override fun componentClicked(whoClicked: HumanEntity, gui: Gui, action: InventoryAction, slot: Int, clickType: ClickType) {
        if (whoClicked is Player) {
            whoClicked.teleport(finalLocation.getLocation(whoClicked))
            gui.closeGui()
        }
    }
}