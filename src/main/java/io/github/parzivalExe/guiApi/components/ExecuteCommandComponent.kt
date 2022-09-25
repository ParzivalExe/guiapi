package io.github.parzivalExe.guiApi.components

import io.github.parzivalExe.guiApi.Gui
import io.github.parzivalExe.guiApi.GuiApiInitializer
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLContent
import io.github.parzivalExe.guiApi.objects.Command
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.inventory.ItemStack

@Suppress("unused", "MemberVisibilityCanBePrivate")
class ExecuteCommandComponent(meta: ComponentMeta, @XMLContent(necessary = true) val commands: ArrayList<Command>)
    : Component(meta) {


    constructor(meta: ComponentMeta, command: Command) : this(meta, arrayListOf(command))

    constructor(meta: ComponentMeta) : this(meta, arrayListOf())

    @Deprecated("DON'T USE: This Constructor is only used for XML and shouldn't be used in Code itself", ReplaceWith("new ExecuteCommandComponent(ComponentMeta)"))
    constructor() : this(ComponentMeta("", ItemStack(Material.WHITE_WOOL)))


    override fun componentClicked(whoClicked: HumanEntity, gui: Gui, action: InventoryAction, slot: Int, clickType: ClickType) {
        if(whoClicked !is Player) {
            whoClicked.sendMessage("${GuiApiInitializer.PREFIX} Normally, only players can click Components")
            return
        }
        if(commands.isEmpty()) {
            whoClicked.sendMessage("${GuiApiInitializer.C_PREFIX} This component has no Commands implemented right now")
            return
        }

        for(command in commands) {
            whoClicked.performCommand(command.buildCommand())
        }
        gui.closeGui()
    }


    fun addCommand(command: Command) {
        commands += command
    }
    fun addCommands(commands: Collection<Command>) {
        this.commands += commands
    }

    fun setCommands(commands: ArrayList<Command>) {
        clearCommands()
        this.commands += commands
    }

    fun clearCommands() = commands.clear()

}