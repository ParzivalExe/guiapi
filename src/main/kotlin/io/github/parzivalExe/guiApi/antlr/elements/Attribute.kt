package io.github.parzivalExe.guiApi.antlr.elements

import io.github.parzivalExe.guiApi.antlr.IXMLRule

class Attribute(val name: String, val value: String) : IXMLRule {

    override fun toString(): String = "$name=\"$value\""

}