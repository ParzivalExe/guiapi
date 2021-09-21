package io.github.parzivalExe.guiApi.antlr.elements

import io.github.parzivalExe.guiApi.antlr.IXMLRule
import java.lang.Exception

@Suppress("MemberVisibilityCanBePrivate")
abstract class Element(val tagName: String) : IXMLRule {

    var attributes = arrayListOf<Attribute>()
    var content: Content? = null

    //region Attribute-Methods

    fun getAttributeWithName(name: String): Attribute =
        getAttributeWithNameOrNull(name) ?: throw Exception("No attribute \'$name\' found for Element \'$tagName\'")
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

    companion object {
        @JvmStatic
        fun getElementFromName(elementName: String): Element = when(elementName) {
            "Library" -> LibraryElement()
            "Include" -> IncludeElement()
            "Gui" -> GuiElement(elementName)
            else -> DynamicElement(elementName)
        }
    }

}