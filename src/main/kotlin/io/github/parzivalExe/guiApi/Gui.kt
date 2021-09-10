package io.github.parzivalExe.guiApi

import io.github.parzivalExe.guiApi.antlr.converter.LookConverter
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLAttribute
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLContent
import io.github.parzivalExe.guiApi.components.Component
import io.github.parzivalExe.guiApi.components.ComponentMeta
import io.github.parzivalExe.guiApi.components.StaticComponent
import io.github.parzivalExe.guiApi.exceptions.ComponentPositionOutOfBoundsException
import io.github.parzivalExe.guiApi.exceptions.GuiCreateException
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.io.File
import javax.xml.parsers.SAXParserFactory

@Suppress("MemberVisibilityCanBePrivate")
class Gui(@XMLAttribute(necessary = true, defaultValue = "NoTitleSet") val title: String) {

    companion object {
        const val MAX_GUI_SIZE = 54
    }

    constructor() : this("NoTitleSet")

    //@XMLAttribute(defaultValue = )
    var id: Int = GuiManager.initializeGui(this)
    @XMLAttribute(defaultValue = "-1")
    var forcedSize = -1
    @XMLAttribute(defaultValue = "true")
    var fillEmptyPlaces = true
    @XMLAttribute(defaultValue = "160:7", converter = LookConverter::class)
    var fillItem = ItemStack(Material.STAINED_GLASS_PANE, 1, 0, 7)
    var inventory: Inventory? = null
    var openedPlayer: Player? = null

    //@Suppress("unused")
    //@XMLAttribute(method = "setComponent(position)")
    //private val componentAttribute = arrayListOf<Component>()

    @XMLContent(true)
    var components = hashMapOf<Component, Int>()


    //region Component-Methods

    fun addComponent(component: Component) {
        var place = component.place
        if(place < 0)
            place = components.keys.size
        component.place = place
        components[component] = place
    }

    fun setComponent(component: Component, position: Int) {
        components[component] = position
    }

    fun removeComponents(startIndex: Int, endIndex: Int) {
        components.filterValues { position -> position in startIndex..endIndex }
                .keys.forEach { component ->
                    component.finalizeComponent()
                    components.remove(component)
                }
    }

    //endregion

    //region CreateGui

    fun openGui(player: Player) {
        val slots: Int = getSlotCount()
        setFillers(slots)
        if(slots <= 0 || slots > 54) {
            Bukkit.getServer().logger.severe("There are problems with the slot-size of Gui $title. You can't create a Gui with a SlotSize of $slots. The size must be between 1 and 54")
            throw GuiCreateException()
        }

        inventory = Bukkit.createInventory(player, slots, title)

        components.forEach { (component, position) ->
            if(position >= slots) {
                Bukkit.getServer().logger.severe("The component $component has a position out of bounds (position: $position, guiSize: $slots)")
                throw ComponentPositionOutOfBoundsException()
            }
            inventory!!.setItem(position, component.look)
            component.place = position
        }
        player.openInventory(inventory)
        openedPlayer = player
    }

    fun getSlotCount(): Int {
        if(sizeIsForced()) {
            return forcedSize
        }
        when (getHighestPos()) {
            in 0..8 ->
                return 9
            in 9..17 ->
                return 18
            in 18..26 ->
                return 27
            in 27..35 ->
                return 36
            in 36..44 ->
                return 45
            in 45..53 ->
                return 54
            else -> {
                Bukkit.getServer().logger.severe("There are problems with the size of Gui $title. You can't set the GUIs size to ${getHighestPos()}. The Size of a gui must be between 1 - 54")
                throw GuiCreateException()
            }
        }
    }

    fun sizeIsForced(): Boolean {
        return forcedSize > 0
    }


    private fun getHighestPos(): Int {
        return components.values.maxOf { position -> position }
    }

    private fun setFillers(slotSize: Int) {
        if(fillEmptyPlaces) {
            for(i in 0 until slotSize) {
                if(!components.values.contains(i)) {
                    components[StaticComponent(ComponentMeta("", ItemStack(fillItem)))] = i
                }
            }
        }
    }

    //endregion

    //region CloseGui

    fun closeGui(): Boolean {
        if(openedPlayer == null) {
            return false
        }
        openedPlayer!!.closeInventory()
        finalizeGui()
        return true
    }

    fun finalizeGui() {
        components.keys.forEach { component ->
            component.place = -1
            component.finalizeComponent()
        }
        GuiManager.finalizeGui(this)
    }

    //endregion


    override fun equals(other: Any?): Boolean {
        if(other is Gui) {
            return other.id == this.id
        }
        return false
    }

    override fun hashCode(): Int {
        return id
    }

    override fun toString(): String {
        return "Gui:\n  id: $id\n  title: $title\n  forcedSize: $forcedSize\n  fillEmptySpaces: $fillEmptyPlaces\n  fillItem: $fillItem"
    }

}