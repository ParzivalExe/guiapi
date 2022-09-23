package io.github.parzivalExe.guiApi.components

import io.github.parzivalExe.guiApi.ExternalGui
import io.github.parzivalExe.guiApi.Gui
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLContent
import io.github.parzivalExe.guiApi.events.FolderOpenedEvent
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.inventory.ItemStack

class Folder(meta: ComponentMeta, @XMLContent var newOpenGui: Gui?) : Component(meta) {

    @Suppress("MemberVisibilityCanBePrivate")
    var isNewGuiOpened = false
        private set

    @XMLContent
    var externalGui: ExternalGui? = null


    @Suppress("unused")
    @Deprecated("DON'T USE: This Constructor is only used for XML and shouldn't be used in Code itself", ReplaceWith("new Folder(ComponentMeta, Gui)"))
    internal constructor() : this(ComponentMeta("", ItemStack(Material.CHEST)), null)

    override fun finalizeComponent() {
        super.finalizeComponent()
        if(!isNewGuiOpened) {
            newOpenGui?.finalizeGui()
        }
    }

    override fun componentClicked(whoClicked: HumanEntity, gui: Gui, action: InventoryAction, slot: Int, clickType: ClickType) {
        if(whoClicked is Player) {
            if(newOpenGui != null) {
                isNewGuiOpened = true
                newOpenGui?.openGui(whoClicked, gui)
                Bukkit.getPluginManager().callEvent(FolderOpenedEvent(this, whoClicked, gui, action, place, clickType, newOpenGui!!))
            }else if(externalGui != null) {
                val clazz = gui.getObject(Gui.SAVE_KEY_OPEN_CLASS) as Class<*>
                val externalGui = externalGui?.getGui(clazz)
                if(externalGui != null) {
                    externalGui.openGui(whoClicked, gui)
                    Bukkit.getPluginManager().callEvent(FolderOpenedEvent(this, whoClicked, gui, action, place, clickType, externalGui))
                }

            }
        }
    }

}