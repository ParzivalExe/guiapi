package io.github.parzivalExe.guiApi.antlr.converter

class StringToStringArrayConverter : Converter {

    override fun attributeStringToValue(attrString: String, defaultValue: Any?): Any? = arrayListOf(attrString.split("\n"))

}