package io.github.parzivalExe.guiApi.antlr.elements

import io.github.parzivalExe.guiApi.antlr.converter.LookConverter
import io.github.parzivalExe.guiApi.components.ComponentMeta
import io.github.parzivalExe.guiApi.components.StaticComponent
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class StaticComponentElement : OldElement() {

    override fun createComponent(): Any {
        val name = getValueForAttribute("name")
        val look = LookConverter().attributeStringToValue(getValueForAttribute("look"), ItemStack(Material.WOOL)) as ItemStack
        val place =  getValueForAttributeOrDefault("place", "-1").toInt()
        val component = StaticComponent(ComponentMeta(name, look))
        component.place = place
        return component
    }

}