package io.github.parzivalExe.guiApi.xml

import org.xml.sax.Attributes

internal class XMLTag(var tagClass: Class<out IXmlTag>, private var xmlAttributes: HashMap<String, Any>, val originalAttributes: Attributes?, var parentTags: ArrayList<XMLTag>) {

    fun getXmlAttributes(): Map<String, Any> {
        return xmlAttributes.filter { xmlAttribute -> !xmlAttribute.key.startsWith("init:") }
    }

    fun getXmlInitAttributes(): Map<String, Any> {
        return xmlAttributes.filter { xmlAttribute -> xmlAttribute.key.startsWith("init:") }
    }

    fun getXmlInitTypes(): Array<Class<*>?> {
        val array = arrayOfNulls<Class<*>>(getXmlInitAttributes().values.size)
        getXmlInitAttributes().values.forEachIndexed { index, initAttribute -> array[index] = initAttribute::class.java }
        return array
    }

    fun toStringWithoutParents(): String {
        var string = "${tagClass.name}: "
        xmlAttributes.forEach { name, value -> string += "$name = $value, " }
        string = string.removeSuffix(", ")
        return string
    }

    override fun toString(): String {
        var string = toStringWithoutParents()
        string += "\n  parents:"
        parentTags.forEachIndexed { index, parentTag -> string += "\n    $index: ${parentTag.toStringWithoutParents()}" }
        return string
    }

}