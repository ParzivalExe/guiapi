package io.github.parzivalExe.guiApi.antlr.elements

class IncludeElement : NewElement("Include") {

    fun CreateIncludeObject(): Include = Include(getValueForAttribute("synonym"), Class.forName(getValueForAttribute("path")))

}