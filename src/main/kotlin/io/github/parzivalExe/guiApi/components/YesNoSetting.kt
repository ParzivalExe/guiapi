package io.github.parzivalExe.guiApi.components

import org.bukkit.inventory.ItemStack

@Suppress("MemberVisibilityCanBePrivate", "unused", "DEPRECATION")
class YesNoSetting(yesMeta: ComponentMeta, noMeta: ComponentMeta) : Settings(arrayOf(SettingOption(yesMeta), SettingOption(noMeta))) {

    constructor(): this(ComponentMeta("YES", ItemStack(351, 1, 0, 10)), ComponentMeta("no", ItemStack(351, 1, 0, 8)))

    var yesSetting = SettingOption(ComponentMeta("YES", ItemStack(351, 1, 0, 10)))
        private set
    var noSetting = SettingOption(ComponentMeta("no", ItemStack(351, 1, 0, 8)))
        private set



}