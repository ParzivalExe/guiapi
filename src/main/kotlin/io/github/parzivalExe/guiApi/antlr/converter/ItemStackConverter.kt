package io.github.parzivalExe.guiApi.antlr.converter

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class ItemStackConverter : Converter{
    override fun attributeStringToValue(attrString: String, defaultValue: Any?): Any? {
        if(attrString.isEmpty())
            return defaultValue

        var string = attrString
        //[1x]type[\[damage\]]
        var amount = 1
        var damage: Short = 0
        if(string.contains(Regex("\\d+x"))) {
            amount = string.split("x")[0].toInt()
            string = string.split("x")[1]
        }
        if(string.contains(Regex("\\[\\d+]"))) {
            damage = string.split("[")[1].removeSuffix("]").toShort()
            string = string.split("[")[0]
        }

        @Suppress("DEPRECATION")
        return ItemStack(Material.getMaterial(string)!!, amount).apply {
            durability = damage
        }
    }
}