package io.github.parzivalExe.guiApi.antlr.elements

import io.github.parzivalExe.guiApi.Gui
import io.github.parzivalExe.guiApi.antlr.converter.LookConverter
import io.github.parzivalExe.guiApi.components.ComponentMeta
import io.github.parzivalExe.guiApi.components.Folder
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.lang.Exception

class FolderElement : OldElement() {

    override fun createComponent(): Any {
        val name = getValueForAttribute("name")
        val look = LookConverter().attributeStringToValue(getValueForAttribute("look"), ItemStack(Material.COMMAND)) as ItemStack

        val guiElement = content?.elements?.find { element -> element is GuiElement } ?: throw Exception("A Folder-Tag always needs a new Gui-Tag as it's child")

        val folder = Folder(Gui("TODO")/*guiElement.createComponent() as Gui*/, ComponentMeta(name, look))
        folder.place = getValueForAttributeOrDefault("place", folder.place.toString()).toInt()
        return folder
    }

}