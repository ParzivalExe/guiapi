package io.github.parzivalExe.guiApi.components

import io.github.parzivalExe.guiApi.Gui
import io.github.parzivalExe.guiApi.antlr.converter.InvItemStackConverter
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLAttribute
import io.github.parzivalExe.guiApi.objects.InvItemStack
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.inventory.ItemStack

class GetItemComponent(@XMLAttribute(defaultValue = "0=35", converter = InvItemStackConverter::class) var items: ArrayList<InvItemStack>, meta: ComponentMeta)
    : Component(meta) {

    @XMLAttribute
    var overrideInInv = true

    @XMLAttribute
    var closeGui = true

    @Suppress("unused")
    constructor(item: InvItemStack, meta: ComponentMeta) : this(arrayListOf(item), meta)
    constructor(meta: ComponentMeta) : this(arrayListOf(InvItemStack(Material.WOOL, 0)), meta)
    @Suppress("unused")
    constructor() : this(ComponentMeta("", ItemStack(Material.WOOL)))


    override fun componentClicked(whoClicked: HumanEntity, gui: Gui, action: InventoryAction, slot: Int, clickType: ClickType) {
        if(whoClicked is Player) {
            for(item in items) {
                when (item.invPosition) {
                    InvItemStack.POSITION_HELMET -> if (whoClicked.inventory.helmet == null || overrideInInv) whoClicked.inventory.helmet =
                        item.itemStack
                    InvItemStack.POSITION_CHESTPLATE -> if (whoClicked.inventory.chestplate == null || overrideInInv) whoClicked.inventory.chestplate =
                        item.itemStack
                    InvItemStack.POSITION_LEGGINGS -> if (whoClicked.inventory.leggings == null || overrideInInv) whoClicked.inventory.leggings =
                        item.itemStack
                    InvItemStack.POSITION_BOOTS -> if (whoClicked.inventory.boots == null || overrideInInv) whoClicked.inventory.boots =
                        item.itemStack
                    else -> if (whoClicked.inventory.getItem(item.invPosition) == null || overrideInInv) whoClicked.inventory.setItem(
                        item.invPosition,
                        item.itemStack
                    )
                }
            }
            whoClicked.updateInventory()
        }
        if(closeGui)
            gui.closeGui()
    }

}