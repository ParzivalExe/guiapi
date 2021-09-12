package io.github.parzivalExe.guiApi.components

import org.bukkit.inventory.ItemStack

@Suppress("unused")
class ComponentMeta(var name: String, private var look: ItemStack) {

    var description = arrayListOf<String>()
    var savedObjects = hashMapOf<String, Any>()

    constructor(name: String, look: ItemStack, description: ArrayList<String>) : this(name, look) {
        this.description = description
    }

    //region simple methods

    fun addDescriptionLine(descriptionLine: String) {
        description.add(descriptionLine)
    }

    fun getDescriptionAsString(): String {
        var string = ""
        description.forEach { line -> string += line + "\n" }
        string.removeSuffix("\n")
        return string
    }

    //endregion


    fun setLook(look: ItemStack) {
        this.look = look
    }

    fun buildItem(): ItemStack {
        val itemMeta = look.itemMeta
        itemMeta.apply {
            displayName = name
            lore = description
        }
        look.itemMeta = itemMeta
        return look
    }

}