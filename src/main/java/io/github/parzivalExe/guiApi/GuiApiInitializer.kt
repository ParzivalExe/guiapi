package io.github.parzivalExe.guiApi

import io.github.parzivalExe.guiApi.commands.GetAmountCommand
import io.github.parzivalExe.guiApi.commands.GuiTestCommand
import io.github.parzivalExe.guiApi.commands.ItemEqualsTestCommand
import io.github.parzivalExe.guiApi.components.ComponentEvents
import io.github.parzivalExe.guiApi.xml.GuiXMLParser
import org.bukkit.plugin.java.JavaPlugin
import org.xml.sax.SAXException
import java.io.File
import java.io.IOException
import javax.xml.parsers.ParserConfigurationException
import javax.xml.parsers.SAXParserFactory

class GuiApiInitializer : JavaPlugin() {

    companion object {
        const val PREFIX = "[GuiAPI]"
        const val C_PREFIX = "§8[§6§lGui§4API§8]§r"
    }


    override fun onEnable() {
        println("$PREFIX The GuiAPI is being initialized...")

        registerEvents()
        val amountCommand = GetAmountCommand()
        getCommand("itemEqualsTest").executor = ItemEqualsTestCommand()
        getCommand("guiTest").executor = GuiTestCommand()
        getCommand("guiAmount").executor = amountCommand
        getCommand("componentAmount").executor = amountCommand

        val parserFactory = SAXParserFactory.newInstance()
        try{
            val parser = parserFactory.newSAXParser()
            val handler = GuiXMLParser()
            parser.parse(File("plugins/GuiAPI/Guis/TestGui.xml"), handler)

        }catch (e: SAXException) {
            e.printStackTrace()
        }catch (e: ParserConfigurationException) {
            e.printStackTrace()
        }catch (e: IOException) {
            e.printStackTrace()
        }

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