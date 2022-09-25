package io.github.parzivalExe.guiApi.antlr.elements

class IncludeElement : Element("Include") {

    fun createIncludeObject(): Include =
        Include(getValueForAttribute("synonym"), Class.forName(getValueForAttribute("path")))

}