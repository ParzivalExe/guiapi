package io.github.parzivalExe.guiApi.antlr.converter

class NoConverter : Converter{

    override fun attributeStringToValue(attrString: String, defaultValue: Any?): Any? = attrString

}