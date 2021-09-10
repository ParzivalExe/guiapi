package io.github.parzivalExe.guiApi.antlr.elements

import io.github.parzivalExe.guiApi.antlr.converter.LookConverter
import io.github.parzivalExe.guiApi.components.ComponentMeta
import io.github.parzivalExe.guiApi.components.SettingOption
import org.bukkit.inventory.ItemStack

class SettingOptionElement : OldElement() {

    override fun createComponent(): Any {
        val name = getValueForAttributeOrDefault("name", "")
        val look = LookConverter().attributeStringToValue(getValueForAttributeOrDefault("look", ""), ItemStack(351, 1, 0, 10)) as ItemStack
        return SettingOption(ComponentMeta(name, look))
    }

}