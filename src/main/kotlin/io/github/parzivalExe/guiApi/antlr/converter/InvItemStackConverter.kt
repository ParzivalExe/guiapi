package io.github.parzivalExe.guiApi.antlr.converter

import io.github.parzivalExe.guiApi.objects.InvItemStack
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class InvItemStackConverter : Converter {

    override fun attributeStringToValue(attrString: String, defaultValue: Any?): Any? {
        if(attrString.isEmpty())
            return defaultValue

        var string = attrString
        //[1x]type[\[damage\]]
        var amount = 1
        var damage: Short = 0
        var position = 0
        if(string.contains(Regex("="))) {
            position = changeStringToPosition(string.split("=")[0])
            string = string.split("=")[1]
        }
        if(string.contains(Regex("\\d+x"))) {
            amount = string.split("x")[0].toInt()
            string = string.split("x")[1]
        }
        if(string.contains(Regex("\\[\\d+]"))) {
            damage = string.split("[")[1].removeSuffix("]").toShort()
            string = string.split("[")[0]
        }

        val material = Material.getMaterial(string)
        @Suppress("DEPRECATION")
        return InvItemStack(material!!, amount, position).apply {
            itemStack.durability = damage
        }
    }

    @Suppress("SpellCheckingInspection")
    private fun changeStringToPosition(string: String): Int =
        when {
            string == "HELMET" -> -1
            string == "CHEST" || string == "CHESTPLATE" -> -2
            string =="LEGGINGS" -> -3
            string == "BOOTS" -> -4
            Regex("LOW\\d+").matches(string) -> Regex("LOW").replace(string, "").toInt()
            Regex("UP\\d+").matches(string) -> Regex("UP").replace(string, "").toInt() + InvItemStack.UP_OFFSET
            else -> string.toInt() + 9
        }

}