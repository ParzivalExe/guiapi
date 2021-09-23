package io.github.parzivalExe.guiApi.components

import io.github.parzivalExe.guiApi.Gui
import io.github.parzivalExe.guiApi.antlr.converter.ItemStackConverter
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLAttribute
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLConstructor
import io.github.parzivalExe.guiApi.events.YesNoSettingChoosenEvent
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.inventory.ItemStack

@Suppress("MemberVisibilityCanBePrivate", "unused", "DEPRECATION")
class YesNoSetting(yesMeta: ComponentMeta, noMeta: ComponentMeta) : Settings(), ComponentClickAction {

    companion object {
        @JvmStatic val KEY_SETTING_TYPE = "settingType"
    }

    @XMLConstructor([
        XMLAttribute(attrName = "yesTitle", defaultValue = "YES"),
        XMLAttribute(attrName = "yesLook", defaultValue = "351:10", converter = ItemStackConverter::class),
        XMLAttribute(attrName = "description")
    ])
    var yesSettingMeta = ComponentMeta("YES", ItemStack(351, 1, 0, 10))
        private set

    @XMLConstructor([
        XMLAttribute(attrName = "noTitle", defaultValue = "no"),
        XMLAttribute(attrName = "noLook", defaultValue = "351:8", converter = ItemStackConverter::class),
        XMLAttribute(attrName = "description")
    ])
    var noSettingMeta = ComponentMeta("no", ItemStack(351, 1, 0, 8))
        private set


    internal constructor(): this(ComponentMeta("YES", ItemStack(351, 1, 0, 10)), ComponentMeta("no", ItemStack(351, 1, 0, 8)))

    init {
        yesSettingMeta = yesMeta
        noSettingMeta = noMeta
    }


    override fun getGuiItem(): ItemStack {
        options.clear()
        options.add(SettingOption(yesSettingMeta.apply {
            savedObjects[KEY_SETTING_TYPE] = "YES"
            addClickListener(this@YesNoSetting)
        }))
        options.add(SettingOption(noSettingMeta.apply {
            savedObjects[KEY_SETTING_TYPE] = "NO"
            addClickListener(this@YesNoSetting)
        }))
        return super.getGuiItem()
    }

    override fun onClick(component: Component, whoClicked: Player, gui: Gui, action: InventoryAction, clickType: ClickType): Boolean {
        if(component.meta.savedObjects.containsKey(KEY_SETTING_TYPE)) {
            Bukkit.getPluginManager().callEvent(
                YesNoSettingChoosenEvent(
                    component.meta.savedObjects[KEY_SETTING_TYPE] == "YES",
                    whoClicked, gui, action, clickType
                )
            )
            return true
        }
        return false
    }


}