package io.github.parzivalExe.guiApi.antlr.elements

import io.github.parzivalExe.guiApi.antlr.converter.LookConverter
import io.github.parzivalExe.guiApi.antlr.converter.OpenOptionConverter
import io.github.parzivalExe.guiApi.components.ComponentMeta
import io.github.parzivalExe.guiApi.components.YesNoOption
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class YesNoOptionElement : OldElement() {

    override fun createComponent(): Any {
        val meta = ComponentMeta(getValueForAttributeOrDefault("name", ""), LookConverter().attributeStringToValue(getValueForAttributeOrDefault("look", ""), ItemStack(Material.REDSTONE_TORCH_ON)) as ItemStack)
        val dialogTitle = getValueForAttributeOrDefault("dialogTitle", meta.name)
        val openOption = OpenOptionConverter().attributeStringToValue(getValueForAttributeOrDefault("optionOption", ""), YesNoOption.OpenOption.UNDER_INVENTORY) as YesNoOption.OpenOption

        val yesNoOption: YesNoOption;

        if(containsAttributeWithName("yesName") || containsAttributeWithName("yesLook") || containsAttributeWithName("noName") || containsAttributeWithName("noLook")) {
            val yesMeta = ComponentMeta(getValueForAttributeOrDefault("yesName", "YES"),
                LookConverter().attributeStringToValue(getValueForAttributeOrDefault("yesLook", ""), ItemStack(Material.WOOL, 1, 0, 5)) as ItemStack)
            val noMeta = ComponentMeta(getValueForAttributeOrDefault("noName", "NO"),
                LookConverter().attributeStringToValue(getValueForAttributeOrDefault("noLook", ""), ItemStack(Material.BARRIER)) as ItemStack)
            yesNoOption = YesNoOption(meta, dialogTitle, yesMeta, noMeta, openOption)
        }else{
            yesNoOption = YesNoOption(meta)
            yesNoOption.openOption = openOption
            yesNoOption.yesNoDialogTitle = dialogTitle
        }
        return yesNoOption
    }

}