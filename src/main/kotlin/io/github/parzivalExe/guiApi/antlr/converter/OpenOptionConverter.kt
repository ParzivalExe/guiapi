package io.github.parzivalExe.guiApi.antlr.converter

import io.github.parzivalExe.guiApi.components.OpenOption

class OpenOptionConverter : Converter {

    override fun attributeStringToValue(attrString: String, defaultValue: Any?): Any? =
        if(attrString.equals("newInventory", true))
            OpenOption.NEW_INVENTORY
        else if(attrString.equals("underInventory", true))
            OpenOption.UNDER_INVENTORY
        else
            defaultValue

}