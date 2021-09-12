package io.github.parzivalExe.guiApi

import io.github.parzivalExe.guiApi.commands.GetAmountCommand
import io.github.parzivalExe.guiApi.commands.GuiTestCommand
import io.github.parzivalExe.guiApi.commands.GuiXMLCommand
import io.github.parzivalExe.guiApi.commands.ItemEqualsTestCommand
import io.github.parzivalExe.guiApi.components.ComponentEvents
import org.bukkit.plugin.java.JavaPlugin

class GuiApiInitializer : JavaPlugin() {

    companion object {
        const val PREFIX = "[GuiAPI]"
        const val C_PREFIX = "§8[§6§lGui§4API§8]§r"
    }


    override fun onEnable() {
        println("$PREFIX The GuiAPI is being initialized...")

        registerConfig()
        registerEvents()
        registerCommands()

        println("$PREFIX The GuiAPI has been initialized!")
    }


    override fun onDisable() {
        println("$PREFIX The GuiAPI is shutting down...")


        println("$PREFIX The GuiAPI has been disabled!")
    }


    private fun registerEvents() {
        println("$PREFIX   All Events are being registered...")

        server.pluginManager.registerEvents(GuiEvents(), this)
        server.pluginManager.registerEvents(ComponentEvents(), this)

        println("$PREFIX   Events have been registered!")
    }

    private fun registerCommands() {
        val amountCommand = GetAmountCommand()
        getCommand("itemEqualsTest").executor = ItemEqualsTestCommand()
        getCommand("guiTest").executor = GuiTestCommand()
        getCommand("guiAmount").executor = amountCommand
        getCommand("componentAmount").executor = amountCommand
        @Suppress("SpellCheckingInspection")
        getCommand("guixml").executor = GuiXMLCommand()
    }

    private fun registerConfig() {
        config.options().copyDefaults(true)
    }



}