package io.github.parzivalExe.guiApi.components

import io.github.parzivalExe.guiApi.Gui
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLAttribute
import io.github.parzivalExe.guiApi.events.EventComponentClickedEvent
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.inventory.ItemStack

/**
 * <h2>general</h2>
 * This is a special component based on the class {@link Component}. If you click this component it will fire the event {@see GuiEventComponentClickedEvent}.
 *
 * @author Parzival
 * @category Component
 * @version 2.0
 * @since 1.0
 * @extends no
 * @initialize yes
 * @basedOn {@link Component}
 */
class EventComponent(componentMeta: ComponentMeta) : Component(componentMeta) {

    @XMLAttribute
    var closeGui = true


    @Suppress("unused")
    @Deprecated("DON'T USE: This Constructor is only used for XML and shouldn't be used in Code itself", ReplaceWith("new EventComponent(ComponentMeta)"))
    internal constructor() : this(ComponentMeta("", ItemStack(Material.WHITE_WOOL)))

    override fun componentClicked(whoClicked: HumanEntity, gui: Gui, action: InventoryAction, slot: Int, clickType: ClickType) {
        Bukkit.getPluginManager().callEvent(EventComponentClickedEvent(this, whoClicked, gui, action, slot, clickType))
        if(closeGui)
            gui.closeGui()
    }

}