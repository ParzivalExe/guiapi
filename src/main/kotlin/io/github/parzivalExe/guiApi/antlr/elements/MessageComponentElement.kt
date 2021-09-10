package io.github.parzivalExe.guiApi.antlr.elements

import io.github.parzivalExe.guiApi.antlr.converter.LookConverter
import io.github.parzivalExe.guiApi.components.ComponentMeta
import io.github.parzivalExe.guiApi.components.MessageComponent
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class MessageComponentElement : OldElement() {


    override fun createComponent(): Any {
        val name = getValueForAttribute("name")
        val look = LookConverter().attributeStringToValue(getValueForAttribute("look"), ItemStack(Material.BOOK_AND_QUILL)) as ItemStack
        val place =  getValueForAttributeOrDefault("place", "-1").toInt()
        val message = getValueForAttributeOrDefault("message", "")
        val component = MessageComponent(ComponentMeta(name, look))
        component.place = place
        component.message = message
        return component
    }

}