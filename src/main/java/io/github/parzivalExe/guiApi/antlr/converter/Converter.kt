package io.github.parzivalExe.guiApi.antlr.converter

interface Converter {

    fun attributeStringToValue(attrString: String, defaultValue: Any?): Any?

}