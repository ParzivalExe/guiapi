package io.github.parzivalExe.guiApi.commands

import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class ItemEqualsTestCommand : CommandExecutor{


    override fun onCommand(sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?): Boolean {
        if(sender is Player) {

            var item = ItemStack(Material.APPLE)
            sender.inventory.setItem(0, item)
            sender.inventory.setItem(2, item)

            sender.sendMessage("equals: ${sender.inventory.getItem(0) == sender.inventory.getItem(2)}")
            val item2 = ItemStack(Material.GOLDEN_APPLE)
            val item3 = ItemStack(Material.GOLDEN_APPLE)
            sender.inventory.setItem(1, item2)
            sender.inventory.setItem(3, item3)
            sender.sendMessage("equalsGolden: ${sender.inventory.getItem(1) == sender.inventory.getItem(3)}")
        }

        return true
    }


}