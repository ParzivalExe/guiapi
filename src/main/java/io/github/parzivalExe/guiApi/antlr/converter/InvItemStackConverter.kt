package io.github.parzivalExe.guiApi.antlr.converter

import io.github.parzivalExe.guiApi.objects.InvItemStack
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class InvItemStackConverter : Converter {

    override fun attributeStringToValue(attrString: String, defaultValue: Any?): Any? {
        if(attrString.isEmpty())
            return defaultValue

        var string = attrString
        var position = InvItemStack.NO_POSITION

        if(string.contains(Regex("="))) {
            position = changeStringToPosition(string.split("=")[0])
            string = string.split("=")[1]
        }
        val item = ItemStackConverter().attributeStringToValue(string, ItemStack(Material.WHITE_WOOL)) as ItemStack

        @Suppress("DEPRECATION")
        return InvItemStack(item, position)
    }

    @Suppress("SpellCheckingInspection")
    private fun changeStringToPosition(string: String): Int =
        when {
            string == "HELMET" -> -1
            string == "CHEST" || string == "CHESTPLATE" -> -2
            string =="LEGGINGS" -> -3
            string == "BOOTS" -> -4
            Regex("LOW\\d+").matches(string) -> Regex("LOW").replace(string, "").toInt()
            Regex("UP\\d+").matches(string) -> Regex("UP").replace(string, "").toInt() + InvItemStack.POSITION_UP_OFFSET
            else -> string.toInt() + 9
        }

}