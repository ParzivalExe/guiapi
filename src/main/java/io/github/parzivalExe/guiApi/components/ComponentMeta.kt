package io.github.parzivalExe.guiApi.components

import io.github.parzivalExe.objectXmlParser.IXmlTag
import io.github.parzivalExe.objectXmlParser.XMLAttribute
import io.github.parzivalExe.objectXmlParser.XMLTag
import org.bukkit.inventory.ItemStack

class ComponentMeta(@XMLAttribute(necessary = true ) var name: String, @XMLAttribute(necessary = true) private var look: ItemStack) : IXmlTag {

    @XMLAttribute
    var description = arrayListOf<String>()
    var savedObjects = hashMapOf<String, Any>()

    companion object {
        @Suppress("unused")
        @JvmStatic
        fun initializeInstance(xmlTag: XMLTag): Any {
            return ComponentMeta(xmlTag.getXmlAttributeByName("name")!!.getConvertedValue() as String, xmlTag.getXmlAttributeByName("look")!!.getConvertedValue() as ItemStack)
        }
    }

    //region simple methods

    fun addDescriptionLine(descriptionLine: String) {
        description.add(descriptionLine)
    }

    fun getDescriptionAsString(): String {
        var string = ""
        description.forEach { line -> string += line + "\n" }
        string.removeSuffix("\n")
        return string
    }

    //endregion


    fun setLook(look: ItemStack) {
        this.look = look
    }

    fun buildItem(): ItemStack {
        val itemMeta = look.itemMeta
        itemMeta.apply {
            displayName = name
            lore = description
        }
        look.itemMeta = itemMeta
        return look
    }

}