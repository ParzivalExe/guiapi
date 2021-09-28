package io.github.parzivalExe.guiApi.objects

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

@Suppress("DEPRECATION", "unused")
class InvItemStack(var itemStack: ItemStack, var invPosition: Int) {

    companion object {
        const val POSITION_HELMET = -1
        const val POSITION_CHESTPLATE = -2
        const val POSITION_LEGGINGS = -3
        const val POSITION_BOOTS = -4
        const val POSITION_LOW_0 = 0
        const val POSITION_LOW_1 = 1
        const val POSITION_LOW_2 = 2
        const val POSITION_LOW_3 = 3
        const val POSITION_LOW_4 = 4
        const val POSITION_LOW_5 = 5
        const val POSITION_LOW_6 = 6
        const val POSITION_LOW_7 = 7
        const val POSITION_LOW_8 = 8
        const val POSITION_UP_OFFSET = 9
    }


    constructor(type: Material, amount: Int, itemDurability: Short, data: Byte, invPosition: Int)
            : this(ItemStack(type, amount, 0, data).apply {
                    durability = itemDurability
              }, invPosition)

    constructor(type: Int, amount: Int, itemDurability: Short, data: Byte, invPosition: Int)
            : this(ItemStack(type, amount, 0, data).apply {
                    durability = itemDurability
              }, invPosition)

    constructor(type: Int, invPosition: Int)
            : this(type, 1, invPosition)

    constructor(type: Int, amount: Int, invPosition: Int)
            : this(ItemStack(type, amount, 0, 0), invPosition)

    constructor(type: Material, invPosition: Int)
            : this(type, 1, invPosition)

    constructor(type: Material, amount: Int, invPosition: Int)
            : this(ItemStack(type, amount, 0, 0), invPosition)

}