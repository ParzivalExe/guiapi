package io.github.parzivalExe.guiApi.commands

import io.github.parzivalExe.guiApi.Gui
import io.github.parzivalExe.guiApi.components.*
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class GuiTestCommand : CommandExecutor{

    override fun onCommand(sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?): Boolean {

        @Suppress("DEPRECATION")
        if(sender is Player) {

            val gui2 = Gui("FolderContent")
            gui2.addComponent(Settings(arrayOf(
                    SettingOption(ComponentMeta("LAME", ItemStack(Material.APPLE))),
                    SettingOption(ComponentMeta("NORMAL", ItemStack(Material.GOLDEN_APPLE))),
                    SettingOption(ComponentMeta("OP", ItemStack(Material.GOLDEN_APPLE, 1, 0, 1))))))
            gui2.setComponent(MessageComponent(ComponentMeta("Record", ItemStack(Material.GOLD_RECORD))).apply { message = "This is a record of the developer" }, 8)
            gui2.setComponent(YesNoOption(ComponentMeta("UnderInv", ItemStack(Material.SIGN))), 2)
            gui2.setComponent(YesNoOption(ComponentMeta("NewInv", ItemStack(Material.SIGN))).apply {
                openOption = YesNoOption.OpenOption.NEW_INVENTORY
            }, 3)
            val yesNoSetting = YesNoSetting()
            gui2.setComponent(yesNoSetting, 5)


            val gui = Gui("TestGui")
            val staticComponent = StaticComponent(ComponentMeta("TestStatic1", ItemStack(Material.GOLDEN_APPLE)))
            val staticComponent2 = StaticComponent(ComponentMeta("TestStatic2", ItemStack(Material.GOLDEN_APPLE)))
            gui.addComponent(staticComponent)
            gui.addComponent(staticComponent2)
            gui.setComponent(Folder(gui2, ComponentMeta("TestFolder", ItemStack(Material.CHEST))), 4)
            gui.openGui(sender)
        }

        return true
    }

}