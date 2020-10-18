package io.github.parzivalExe.guiApi

import io.github.parzivalExe.guiApi.commands.GetAmountCommand
import io.github.parzivalExe.guiApi.commands.GuiTestCommand
import io.github.parzivalExe.guiApi.commands.GuiXMLCommand
import io.github.parzivalExe.guiApi.commands.ItemEqualsTestCommand
import io.github.parzivalExe.guiApi.components.ComponentEvents
import io.github.parzivalExe.objectXmlParser.ObjectXMLParser
import io.github.parzivalExe.objectXmlParser.ValueParser
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class GuiApiInitializer : JavaPlugin() {

    companion object {
        const val PREFIX = "[GuiAPI]"
        const val C_PREFIX = "§8[§6§lGui§4API§8]§r"
    }


    override fun onEnable() {
        println("$PREFIX The GuiAPI is being initialized...")


        ObjectXMLParser.parsableTypes[ItemStack::class.java] = object: ValueParser {
            override fun parse(orgString: String): Any {
                var string = orgString
                //[1x]type[:data][\[damage\]]
                var type = 0
                var amount = 1
                var damage: Short = 0
                var data: Byte = 0
                if(string.contains(Regex("\\d+x"))) {
                    amount = string.split("x")[0].toInt()
                    string = string.split("x")[1]
                }
                if(string.contains(Regex("\\[\\d+]}"))) {
                    damage = string.split("[")[1].removeSuffix("]").toShort()
                    string = string.split("[")[0]
                }
                if(string.contains(Regex(":\\d+"))) {
                    data = string.split(":")[1].toByte()
                    string = string.split(":")[0]
                }
                type = string.toInt()

                @Suppress("DEPRECATION")
                return ItemStack(type, amount, damage, data)
            }
        }

        registerEvents()
        val amountCommand = GetAmountCommand()
        getCommand("itemEqualsTest").executor = ItemEqualsTestCommand()
        getCommand("guiTest").executor = GuiTestCommand()
        getCommand("guiAmount").executor = amountCommand
        getCommand("componentAmount").executor = amountCommand
        getCommand("guixml").executor = GuiXMLCommand()

        println("$PREFIX The GuiAPI has been initialized!")
    }


    override fun onDisable() {
        println("$PREFIX The GuiAPI is shutting down...")


        println("$PREFIX The GuiAPI has been disabled!")
    }


    fun registerEvents() {
        println("$PREFIX   All Events are being registered...")

        server.pluginManager.registerEvents(GuiEvents(), this)
        server.pluginManager.registerEvents(ComponentEvents(), this)

        println("$PREFIX   Events have been registered!")
    }



}