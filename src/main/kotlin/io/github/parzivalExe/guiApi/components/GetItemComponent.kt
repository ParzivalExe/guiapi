package io.github.parzivalExe.guiApi.components

import io.github.parzivalExe.guiApi.Gui
import io.github.parzivalExe.guiApi.antlr.converter.InvItemStackConverter
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLAttribute
import io.github.parzivalExe.guiApi.events.GetItemComponentClickedEvent
import io.github.parzivalExe.guiApi.objects.InvItemStack
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.inventory.ItemStack

class GetItemComponent(meta: ComponentMeta, @XMLAttribute(defaultValue = "[0=WHITE_WOOL]", converter = InvItemStackConverter::class) var items: ArrayList<InvItemStack>)
    : Component(meta) {

    @XMLAttribute
    var overrideInInv = true

    @XMLAttribute
    var closeGui = true

    @Suppress("unused")
    constructor(meta: ComponentMeta, item: InvItemStack) : this(meta, arrayListOf(item))
    constructor(meta: ComponentMeta) : this(meta, arrayListOf())
    @Suppress("unused")
    constructor() : this(ComponentMeta("", ItemStack(Material.WHITE_WOOL)))


    override fun componentClicked(whoClicked: HumanEntity, gui: Gui, action: InventoryAction, slot: Int, clickType: ClickType) {
        if(whoClicked is Player) {
            for(item in items) {
                items.forEach { item -> item.givePlayerItem(whoClicked, overrideInInv) }
            }
            whoClicked.updateInventory()
            Bukkit.getPluginManager().callEvent(GetItemComponentClickedEvent(this, whoClicked, gui, action, place, clickType, items.toTypedArray()))
        }
        if(closeGui)
            gui.closeGui()
    }

}