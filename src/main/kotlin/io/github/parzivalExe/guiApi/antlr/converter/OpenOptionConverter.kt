package io.github.parzivalExe.guiApi.antlr.converter

import io.github.parzivalExe.guiApi.components.YesNoOption

class OpenOptionConverter : Converter {

    override fun attributeStringToValue(attrString: String, defaultValue: Any?): Any? {
        if(attrString.equals("newInventory", true))
            return YesNoOption.OpenOption.NEW_INVENTORY
        else if(attrString.equals("underInventory", true))
            return YesNoOption.OpenOption.UNDER_INVENTORY
        else
            return defaultValue
    }

}