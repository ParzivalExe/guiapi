package io.github.parzivalExe.guiApi.antlr.converter

import io.github.parzivalExe.guiApi.PathOrigin

class PathOriginConverter : Converter {


    override fun attributeStringToValue(attrString: String, defaultValue: Any?): Any =
        if(attrString.equals("PC_ORIGIN", true))
            PathOrigin.PC_ORIGIN
        else if(attrString.equals("SERVER_ORIGIN", true))
            PathOrigin.SERVER_ORIGIN
        else
            PathOrigin.PROJECT_ORIGIN


}