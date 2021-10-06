package io.github.parzivalExe.guiApi.antlr.converter

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class ItemStackConverter : Converter {
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
        if(string.contains(Regex("\\[\\d+]"))) {
            damage = string.split("[")[1].removeSuffix("]").toShort()
            string = string.split("[")[0]
        }
        if(string.contains(Regex(":\\d+"))) {
            data = string.split(":")[1].toByte()
            string = string.split(":")[0]
        }
        @Suppress("DEPRECATION")
        val material =
            if(string.matches(Regex("\\d*")))
                Material.getMaterial(string.toInt())
            else
                Material.getMaterial(string)
        @Suppress("DEPRECATION")
        return ItemStack(material, amount, 0, data).apply {
            if(material.maxDurability > 0) durability = damage
        }
    }
}