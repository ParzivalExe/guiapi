package io.github.parzivalExe.guiApi.components

import io.github.parzivalExe.objectXmlParser.IXmlTag
import io.github.parzivalExe.objectXmlParser.XMLAttribute
import io.github.parzivalExe.objectXmlParser.XMLTag

class SettingOption(@XMLAttribute(necessary = true) var meta: ComponentMeta) : IXmlTag {

    companion object {
        @Suppress("unused")
        @JvmStatic
        fun initializeInstance(xmlTag: XMLTag): Any {
            return SettingOption(xmlTag.getXmlAttributeByName("meta")!!.getConvertedValue() as ComponentMeta)
        }
    }

}