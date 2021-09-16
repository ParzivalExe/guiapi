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

class TeleportComponent(@XMLAttribute(necessary = true, attrName = "location", defaultValue = "*:*,*,*", converter = TeleportLocationConverter::class)
                        var finalLocation: TeleportLocation, componentMeta: ComponentMeta) : Component(componentMeta) {

    constructor(componentMeta: ComponentMeta) : this(TeleportLocation(), componentMeta)

    constructor() : this(ComponentMeta("", ItemStack(Material.WOOL)))

    override fun componentClicked(whoClicked: HumanEntity, gui: Gui, action: InventoryAction, slot: Int, clickType: ClickType) {
        if (whoClicked is Player) {
            whoClicked.teleport(finalLocation.getLocation(whoClicked))
            gui.closeGui()
        }
    }
}