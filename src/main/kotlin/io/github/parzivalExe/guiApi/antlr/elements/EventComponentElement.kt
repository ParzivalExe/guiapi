package io.github.parzivalExe.guiApi.antlr.elements

import io.github.parzivalExe.guiApi.antlr.converter.LookConverter
import io.github.parzivalExe.guiApi.components.ComponentMeta
import io.github.parzivalExe.guiApi.components.EventComponent
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class EventComponentElement : OldElement() {


    override fun createComponent(): Any {
        val name = getValueForAttribute("name")
        val look = LookConverter().attributeStringToValue(getValueForAttribute("look"), ItemStack(Material.COMMAND)) as ItemStack
        val place =  getValueForAttributeOrDefault("place", "-1").toInt()
        val component = EventComponent(ComponentMeta(name, look))
        component.place = place
        return component
    }

}