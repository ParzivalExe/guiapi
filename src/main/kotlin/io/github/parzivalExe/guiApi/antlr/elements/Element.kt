package io.github.parzivalExe.guiApi.antlr.elements

import io.github.parzivalExe.guiApi.antlr.IXMLRule
import io.github.parzivalExe.guiApi.components.Component
import java.lang.Exception

abstract class OldElement : IXMLRule{

    var name = ""
    var attributes = arrayListOf<Attribute>()
    var content: Content? = null

    abstract fun createComponent(): Any


    //region Attribute-Methods

    fun getAttributeWithName(name: String): Attribute =
        getAttributeWithNameOrNull(name) ?: throw Exception("No attribute \'$name\' found for Element \'${this.name}\'")
    fun getAttributeWithNameOrNull(name: String): Attribute? =
        attributes.find { it.name == name }
    fun getValueForAttribute(attrName: String): String =
        getAttributeWithName(attrName).value
    fun getValueForAttributeOrNull(attrName: String): String? =
        getAttributeWithNameOrNull(attrName)?.value
    fun getValueForAttributeOrDefault(attrName: String, default: String): String =
        getAttributeWithNameOrNull(attrName)?.value ?: default

    fun containsAttributeWithName(name: String): Boolean =
        attributes.any { it.name == name }

    //endregion


    /*companion object {
        @JvmStatic
        fun getElementFromName(elementName: String): OldElement = when(elementName) {
            "Gui" -> GuiElement()
            "EventComponent" -> EventComponentElement()
            "MessageComponent" -> MessageComponentElement()
            "StaticComponent" -> StaticComponentElement()
            "Settings" -> SettingsElement()
            "SettingOption" -> SettingOptionElement()
            "YesNoOption" -> YesNoOptionElement()
            "Folder" -> FolderElement()
            else -> throw Exception("The Tag \'$elementName\' is not supported as a Component")
        }
    }*/

}