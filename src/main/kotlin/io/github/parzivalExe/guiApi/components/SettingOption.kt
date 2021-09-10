package io.github.parzivalExe.guiApi.components

import io.github.parzivalExe.guiApi.antlr.converter.ItemStackConverter
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLAttribute
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLConstructor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack


class SettingOption(@XMLConstructor([XMLAttribute(true, "title", ""), XMLAttribute(true, "look", "166", ItemStackConverter::class)])
                    var meta: ComponentMeta) {

    constructor(): this(ComponentMeta("", ItemStack(Material.BARRIER))) {}

}