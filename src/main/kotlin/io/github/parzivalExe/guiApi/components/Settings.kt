package io.github.parzivalExe.guiApi.components

import io.github.parzivalExe.guiApi.Gui
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLContent
import io.github.parzivalExe.guiApi.events.SettingsClickedEvent
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.inventory.ItemStack

open class Settings(@XMLContent var options: Array<SettingOption>) : Component(options[0].meta) {

    @Suppress("unused", "DEPRECATION")
    internal constructor() : this(arrayOf(
        SettingOption(ComponentMeta("beginner", ItemStack(Material.APPLE))),
        SettingOption(ComponentMeta("Advanced", ItemStack(Material.GOLDEN_APPLE))),
        SettingOption(ComponentMeta("PROFESSIONAL", ItemStack(Material.GOLDEN_APPLE, 1, 0, 1)))))

    private var activatedOption = 0
        set(value) {
            field = value
            meta = options[value].meta
        }


    @Suppress("unused")
    fun getActivatedOption(): SettingOption {
        return options[activatedOption]
    }


    override fun componentClicked(whoClicked: HumanEntity, gui: Gui, action: InventoryAction, slot: Int, clickType: ClickType) {
        if(whoClicked is Player) {
            val clickedOption = activatedOption
            if (activatedOption + 1 >= options.size)
                activatedOption = 0
            else
                activatedOption++

            meta = options[activatedOption].meta
            gui.openGui(whoClicked)

            Bukkit.getPluginManager().callEvent(SettingsClickedEvent(this, options[clickedOption], options[activatedOption], whoClicked, gui, action, slot, clickType))
        }
    }

}