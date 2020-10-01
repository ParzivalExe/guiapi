package io.github.parzivalExe.guiApi.xml

internal class XMLTag(var tagClass: Class<out IXmlTag>, var xmlAttributes: HashMap<String, String>, var parentTags: ArrayList<XMLTag>) {


    fun toStringWithoutParents(): String {
        var string = "${tagClass.name}: "
        xmlAttributes.forEach { name, value -> string += "$name: ${value::class.java.typeName} = $value, " }
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