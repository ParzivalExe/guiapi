package io.github.parzivalExe.guiApi.components

import io.github.parzivalExe.guiApi.Gui
import io.github.parzivalExe.guiApi.antlr.converter.ItemStackConverter
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLAttribute
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLConstructor
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLContent
import io.github.parzivalExe.guiApi.events.SettingsClickedEvent
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.inventory.ItemStack

open class Settings(@XMLContent var options: ArrayList<SettingOption>) : Component(options[0].meta) {

    @Deprecated("DON'T USE: This Constructor is only used for XML and shouldn't be used in Code itself", ReplaceWith("new Settings(ArrayList<SettingOption>)"))
    internal constructor() : this(arrayListOf(
        SettingOption(ComponentMeta("beginner", ItemStack(Material.APPLE))),
        SettingOption(ComponentMeta("Advanced", ItemStack(Material.GOLDEN_APPLE))),
        SettingOption(ComponentMeta("PROFESSIONAL", ItemStack(Material.ENCHANTED_GOLDEN_APPLE))))
    )

    private var activatedOption = 0


    @Suppress("MemberVisibilityCanBePrivate")
    fun getActivatedOption(): SettingOption {
        return options[activatedOption]
    }

    override fun getGuiItem(): ItemStack {
        return getActivatedOption().meta.buildItem()
    }

    override var meta: ComponentMeta
        get() {
            return getActivatedOption().meta
        }
        set(value) {
            super.meta = value
        }


    override fun componentClicked(whoClicked: HumanEntity, gui: Gui, action: InventoryAction, slot: Int, clickType: ClickType) {
        if(whoClicked is Player) {
            val clickedOption = activatedOption
            if (activatedOption + 1 >= options.size)
                activatedOption = 0
            else
                activatedOption++

            //meta = options[activatedOption].meta
            gui.refreshInventory()

            Bukkit.getPluginManager().callEvent(SettingsClickedEvent(this, options[clickedOption], options[activatedOption], whoClicked, gui, action, slot, clickType))
        }
    }

}