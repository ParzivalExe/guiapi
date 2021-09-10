package io.github.parzivalExe.guiApi

import io.github.parzivalExe.guiApi.antlr.Visitor
import io.github.parzivalExe.guiApi.antlr.grammar.XMLLexer
import io.github.parzivalExe.guiApi.antlr.grammar.XMLParser
import io.github.parzivalExe.guiApi.commands.GetAmountCommand
import io.github.parzivalExe.guiApi.commands.GuiTestCommand
import io.github.parzivalExe.guiApi.commands.GuiXMLCommand
import io.github.parzivalExe.guiApi.commands.ItemEqualsTestCommand
import io.github.parzivalExe.guiApi.components.ComponentEvents
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

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
        getCommand("guixml").executor = GuiXMLCommand()

        enableAntlr4()

        println("$PREFIX The GuiAPI has been initialized!")
    }


    private fun enableAntlr4() {
        val lexer = XMLLexer(CharStreams.fromFileName("GUIs/SimpleTestGui.xml"))
        val token = CommonTokenStream(lexer)
        val parser = XMLParser(token)

        val documentContext = parser.document()

        val visitor = Visitor(documentContext)
        val gui = visitor.buildGui()
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