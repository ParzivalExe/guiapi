package io.github.parzivalExe.guiApi.antlr.converter

import io.github.parzivalExe.guiApi.components.YesNoOption

class OpenOptionConverter : Converter {

    override fun attributeStringToValue(attrString: String, defaultValue: Any?): Any? =
        if(attrString.equals("newInventory", true))
            YesNoOption.OpenOption.NEW_INVENTORY
        else if(attrString.equals("underInventory", true))
            YesNoOption.OpenOption.UNDER_INVENTORY
        else
            defaultValue

}