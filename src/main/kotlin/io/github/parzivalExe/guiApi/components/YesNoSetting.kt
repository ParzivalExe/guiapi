package io.github.parzivalExe.guiApi.components

import io.github.parzivalExe.guiApi.antlr.converter.ItemStackConverter
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLAttribute
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLConstructor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

@Suppress("MemberVisibilityCanBePrivate", "unused", "DEPRECATION")
class YesNoSetting(yesMeta: ComponentMeta, noMeta: ComponentMeta) : Settings() {

    @XMLConstructor([
        XMLAttribute(attrName = "yesTitle", defaultValue = "YES"),
        XMLAttribute(attrName = "yesLook", defaultValue = "351:10", converter = ItemStackConverter::class),
        XMLAttribute(attrName = "description")
    ])
    var yesSettingMeta = ComponentMeta("YES", ItemStack(Material.GREEN_DYE))
        private set

    @XMLConstructor([
        XMLAttribute(attrName = "noTitle", defaultValue = "no"),
        XMLAttribute(attrName = "noLook", defaultValue = "351:8", converter = ItemStackConverter::class),
        XMLAttribute(attrName = "description")
    ])
    var noSettingMeta = ComponentMeta("no", ItemStack(Material.RED_DYE))
        private set


    internal constructor(): this(ComponentMeta("YES", ItemStack(Material.GREEN_DYE)), ComponentMeta("no", ItemStack(Material.RED_DYE)))

    init {
        yesSettingMeta = yesMeta
        noSettingMeta = noMeta
    }


    override fun getGuiItem(): ItemStack {
        options.clear()
        options.add(SettingOption(yesSettingMeta))
        options.add(SettingOption(noSettingMeta))
        return super.getGuiItem()
    }


}