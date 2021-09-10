package io.github.parzivalExe.guiApi.components

import io.github.parzivalExe.guiApi.Gui
import io.github.parzivalExe.guiApi.events.EventComponentClickedEvent
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.inventory.ItemStack

/**
 * <h2>general</h2>
 * This is a special component based on the class {@link Component}. If you will click this component it will fire the event {@see GuiEventComponentClickedEvent}.
 *
 * <h2>infos</h2>
 * You can initialize an event component simple with the Constructor of this class. PLEASE DIDN'T EXTENDS THIS CLASS TO ONE OF YOUR CLASSES because it is
 * already a special Component
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


    internal constructor() : this(ComponentMeta("", ItemStack(Material.WOOL)))

    override fun componentClicked(whoClicked: HumanEntity, gui: Gui, action: InventoryAction, slot: Int, clickType: ClickType) {
        Bukkit.getPluginManager().callEvent(EventComponentClickedEvent(this, whoClicked, gui, action, slot, clickType))
    }

}