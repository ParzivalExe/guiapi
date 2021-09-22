package io.github.parzivalExe.guiApi.commands

import io.github.parzivalExe.guiApi.Gui
import io.github.parzivalExe.guiApi.GuiApiInitializer
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class GuiXMLCommand : CommandExecutor {


    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender is Player && args.size == 1) {
            @Suppress("SpellCheckingInspection")
            val gui = Gui.createGuiFromFile("plugins/GuiAPI/Guis/${args[0]}.mgui")
            gui.openGui(sender)
            return true
        }
        @Suppress("SpellCheckingInspection")
        sender.sendMessage("${GuiApiInitializer.PREFIX} The Command needs the name of the file: /guixml [fileName]")
        return true
    }


}