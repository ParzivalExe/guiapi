package io.github.parzivalExe.guiApi.antlr.converter

import org.bukkit.inventory.ItemStack

class ItemStackConverter : Converter{
    override fun attributeStringToValue(attrString: String, defaultValue: Any?): Any? {
        if(attrString.isEmpty())
            return defaultValue

        var string = attrString
        //[1x]type[:data][\[damage\]]
        var amount = 1
        var damage: Short = 0
        var data: Byte = 0
        if(string.contains(Regex("\\d+x"))) {
            amount = string.split("x")[0].toInt()
            string = string.split("x")[1]
        }
        if(string.contains(Regex("\\[\\d+]}"))) {
            damage = string.split("[")[1].removeSuffix("]").toShort()
            string = string.split("[")[0]
        }
        if(string.contains(Regex(":\\d+"))) {
            data = string.split(":")[1].toByte()
            string = string.split(":")[0]
        }
        val type: Int = string.toInt()

        @Suppress("DEPRECATION")
        return ItemStack(type, amount, damage, data)
    }
}