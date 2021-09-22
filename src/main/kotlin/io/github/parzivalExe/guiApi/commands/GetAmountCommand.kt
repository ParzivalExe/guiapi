package io.github.parzivalExe.guiApi.commands

import io.github.parzivalExe.guiApi.GuiManager
import io.github.parzivalExe.guiApi.components.ComponentManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class GetAmountCommand : CommandExecutor {


    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        when (command.name) {
            "guiAmount" -> {
                sender.sendMessage("guis.size: ${GuiManager.getAllGuis().size}")
            }
            "componentAmount" -> {
                sender.sendMessage("components.size: ${ComponentManager.getAllComponents().size}")
            }
            "componentList" -> {
                sender.sendMessage("-------components--------")
                ComponentManager.getAllComponents().forEach {
                    sender.sendMessage("${it::class.simpleName} {${it.id}]: name = ${it.meta.title}, place = ${it.place}")
                }
                sender.sendMessage("------------------------")
            }
        }
        return true
    }
}