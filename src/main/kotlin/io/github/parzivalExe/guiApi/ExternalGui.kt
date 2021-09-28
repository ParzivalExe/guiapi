package io.github.parzivalExe.guiApi

import io.github.parzivalExe.guiApi.antlr.converter.PathOriginConverter
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLAttribute
import org.bukkit.entity.Player

@Suppress("unused")
class ExternalGui(@XMLAttribute(necessary = true) var path: String, @XMLAttribute var fileType: String, @XMLAttribute(converter = PathOriginConverter::class) var pathOrigin: PathOrigin) {


    constructor(path: String, inProject: PathOrigin) : this(path, "mgui", inProject)
    internal constructor() : this("", PathOrigin.PROJECT_ORIGIN)


    fun getGui(): Gui? {
        val clazz = Class.forName(Thread.currentThread().stackTrace[2].className)
        return getGui(clazz)
    }

    fun getGui(pluginClass: Class<*>): Gui? {
        if(path.isEmpty())
            return null
        return Gui.createGui(path, fileType, pathOrigin, pluginClass)
    }

    fun openGui(player: Player): Boolean {
        val gui = getGui() ?: return false
        gui.openGui(player)
        return true
    }
    fun openGui(player: Player, pluginClass: Class<*>): Boolean {
        val gui = getGui(pluginClass) ?: return false
        gui.openGui(player, pluginClass)
        return true
    }

}