package io.github.parzivalExe.guiApi.components

import io.github.parzivalExe.guiApi.Gui
import io.github.parzivalExe.guiApi.events.SettingsClickedEvent
import org.bukkit.Bukkit
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction

class Settings(var options: Array<SettingOption>) : Component(options[0].meta) {

    var activatedOption = 0
        set(value) {
            field = value
            meta = options[value].meta
        }

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