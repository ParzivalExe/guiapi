package io.github.parzivalExe.guiApi.components

import io.github.parzivalExe.guiApi.antlr.converter.ItemStackConverter
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLAttribute
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLConstructor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack


class SettingOption(@XMLConstructor([
    XMLAttribute(true, "title", ""),
    XMLAttribute(true, "look", "166", ItemStackConverter::class),
    XMLAttribute(attrName = "description")])
                    var meta: ComponentMeta) {

    @Suppress("unused")
    internal constructor(): this(ComponentMeta("", ItemStack(Material.BARRIER)))

    constructor(title: String, look: ItemStack): this(ComponentMeta(title, look))
    constructor(title: String, look: ItemStack, description: ArrayList<String>): this(ComponentMeta(title, look, description))

    var title: String
        get() = meta.title
        set(value) {
            meta.title = value
        }
    var look: ItemStack
        get() = meta.buildItem()
        set(value) = meta.setLook(value)
    var description: ArrayList<String>
        get() = meta.description
        set(value) {
            meta.description = value
        }

}