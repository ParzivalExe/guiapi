package io.github.parzivalExe.guiApi.components

import io.github.parzivalExe.guiApi.Gui
import io.github.parzivalExe.guiApi.antlr.converter.ItemStackConverter
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLAttribute
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLConstructor
import io.github.parzivalExe.guiApi.events.NoOptionClickedEvent
import io.github.parzivalExe.guiApi.events.YesOptionClickedEvent
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.inventory.ItemStack

class YesNoOption(meta: ComponentMeta) : AdditionalOptionsComponent(meta), ComponentClickAction {


    companion object {
        @JvmStatic val YES_NO_OPTION_KEY = "yesNoOption"
    }


    constructor(): this(ComponentMeta("", ItemStack(Material.WOOL)))

    @Suppress("DEPRECATION")
    @XMLConstructor([XMLAttribute(attrName = "yesTitle", defaultValue = "YES"), XMLAttribute(attrName = "yesLook", defaultValue = "35:5", converter = ItemStackConverter::class)])
    var yesMeta = ComponentMeta("YES", ItemStack(35, 1, 0, 5))

    /*val yesOption get() = StaticComponent(yesMeta.apply {
        savedObjects[YesNoOption.YES_NO_OPTION_KEY] = this@YesNoOptionNew
        clickAction = this@YesNoOptionNew
        place = 3
    })*/

    var yesOption: StaticComponent? = null

    @XMLConstructor([XMLAttribute(attrName = "noTitle", defaultValue = "no"), XMLAttribute(attrName = "noLook", defaultValue = "166", converter = ItemStackConverter::class)])
    var noMeta = ComponentMeta("no", ItemStack(Material.BARRIER))

    /*val noOption get() = StaticComponent(noMeta.apply {
        savedObjects[YesNoOption.YES_NO_OPTION_KEY] = this@YesNoOptionNew
        clickAction = this@YesNoOptionNew
        place = 5
    })*/

    var noOption: StaticComponent? = null

    override fun componentClicked(whoClicked: HumanEntity, gui: Gui, action: InventoryAction, slot: Int, clickType: ClickType) {
        if(isOpened) {
            //CLOSE
            super.componentClicked(whoClicked, gui, action, slot, clickType)
            yesOption?.finalizeComponent()
            noOption?.finalizeComponent()
            additionalComponents.clear()
        }else{
            //OPEN
            yesOption = StaticComponent(yesMeta.apply {
                savedObjects[YES_NO_OPTION_KEY] = this@YesNoOption
            }).apply {
                place = 3
                addClickListener(this@YesNoOption)
            }
            noOption = StaticComponent(noMeta.apply {
                savedObjects[YES_NO_OPTION_KEY] = this@YesNoOption
            }).apply {
                place = 5
                addClickListener(this@YesNoOption)
            }
            additionalComponents.add(yesOption!!)
            additionalComponents.add(noOption!!)
            super.componentClicked(whoClicked, gui, action, slot, clickType)
        }
    }

    override fun onClick(component: Component, whoClicked: Player, gui: Gui, action: InventoryAction, clickType: ClickType): Boolean {
        if(component == yesOption) {
            if(yesOption == null)
                return false

            if(openOption == OpenOption.UNDER_INVENTORY)
                openUnderInventory(whoClicked, gui, action, clickType)
            else
                gui.closeGui()
            Bukkit.getPluginManager().callEvent(YesOptionClickedEvent(yesOption!!.meta.savedObjects[YES_NO_OPTION_KEY] as YesNoOption, component as StaticComponent, gui, whoClicked, action, component.place, clickType))
            return true
        }else if(component == noOption) {
            if(noOption == null)
                return false

            if(openOption == OpenOption.UNDER_INVENTORY)
                openUnderInventory(whoClicked, gui, action, clickType)
            else
                gui.closeGui()
            Bukkit.getPluginManager().callEvent(NoOptionClickedEvent(noOption!!.meta.savedObjects[YES_NO_OPTION_KEY] as YesNoOption, component as StaticComponent, gui, whoClicked, action, component.place, clickType))
            return true
        }
        return false
    }

}