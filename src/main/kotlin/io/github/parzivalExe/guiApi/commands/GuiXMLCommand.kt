package io.github.parzivalExe.guiApi.commands

import io.github.parzivalExe.guiApi.Gui
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.io.File

class GuiXMLCommand : CommandExecutor {


    override fun onCommand(sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?): Boolean {
        if(sender is Player) {
            //val gui = Gui.createFromXML(File("D:/Users/Lasse/Documents/Minecraft/Network/Lobby 1/plugins/GuiAPI/Guis/TestGui.xml"))
            //gui!!.openGui(sender)
        }
        return true
    }


}