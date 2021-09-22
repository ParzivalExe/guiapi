package io.github.parzivalExe.guiApi

import io.github.parzivalExe.guiApi.antlr.Visitor
import io.github.parzivalExe.guiApi.antlr.converter.ItemStackConverter
import io.github.parzivalExe.guiApi.antlr.grammar.XMLLexer
import io.github.parzivalExe.guiApi.antlr.grammar.XMLParser
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLAttribute
import io.github.parzivalExe.guiApi.components.Component
import io.github.parzivalExe.guiApi.components.ComponentMeta
import io.github.parzivalExe.guiApi.components.FreeSpaceComponent
import io.github.parzivalExe.guiApi.exceptions.GuiCreateException
import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.io.File
import java.io.InputStream

@Suppress("MemberVisibilityCanBePrivate")
class Gui(@XMLAttribute(necessary = true, defaultValue = "NoTitleSet") val title: String) {

    companion object {
        const val MAX_GUI_SIZE = 54

        private const val FILL_ITEM = "fillItem"

        @JvmStatic
        fun createGuiFromFile(path: String): Gui = createGuiFromCharStream(CharStreams.fromFileName(path))
        @JvmStatic
        @Suppress("unused")
        fun createGuiFromInputStream(inputStream: InputStream): Gui = createGuiFromCharStream(CharStreams.fromStream(inputStream))
        @JvmStatic
        @Suppress("unused")
        fun createGuiFromFile(file: File): Gui = createGuiFromCharStream(CharStreams.fromStream(file.inputStream()))

        private fun createGuiFromCharStream(charStream: CharStream): Gui {
            val lexer = XMLLexer(charStream)
            val token = CommonTokenStream(lexer)
            val parser = XMLParser(token)

            val documentContext = parser.document()

            val visitor = Visitor(documentContext)
            return visitor.buildGui()
        }
    }

    constructor() : this("NoTitleSet")

    var id: Int = GuiManager.initializeGui(this)
    @XMLAttribute
    var forcedSize = -1
    @XMLAttribute
    var fillEmptyPlaces = true
    @Suppress("DEPRECATION")
    @XMLAttribute(defaultValue = "160:7", converter = ItemStackConverter::class)
    var fillItem = ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1, 0, 7)

    var inventory: Inventory? = null
    var openedPlayer: Player? = null

    private val registeredComponents = hashMapOf<Component, Int>()

    private val registeredFillers = hashMapOf<Component, Int>()


    //region Component-Methods

    //region Public-Component Methods

    fun addComponent(component: Component) {
        registerComponent(component)
    }

    fun setComponent(component: Component, position: Int) = addComponent(component.apply { place = position })

    fun hasComponent(component: Component) = isComponentRegistered(component) || isFillerRegistered(component)
    @Suppress("unused")
    fun hasComponentAtPosition(position: Int) = isComponentRegisteredAtPosition(position) || isFillerRegisteredAtPosition(position)

    fun getPositionOfComponent(component: Component): Int {
        var position = getRegisteredPositionOfComponent(component)
        if(position >= 0)
            return position
        position = getRegisteredPositionOfFiller(component)
        return position
    }

    fun removeComponents(startPosition: Int, endPosition: Int) = removeRegisteredComponents(startPosition, endPosition)
    @Suppress("unused")
    fun removeComponent(position: Int) = removeRegisteredComponentAtPosition(position)
    @Suppress("unused")
    fun removeComponent(components: Array<Component>) = removeRegisteredComponents(components)
    fun removeComponent(component: Component) = removeRegisteredComponent(component)

    @Suppress("unused")
    fun changeComponentPosition(component: Component, newPosition: Int) = changeRegisteredComponentPosition(component, newPosition)

    @Suppress("unused")
    fun changeOutComponent(oldComponent: Component, newComponent: Component) = changeOutRegisteredComponent(oldComponent, newComponent)

    //endregion


    //region RegisteredComponents

    private fun registerComponent(component: Component) {
        var place = component.place
        if(place < 0) {
            place = getFreeRegisteredPlace()
            putRegisteredComponentIntoPosition(component, place)
        }else{
            putRegisteredComponentIntoPosition(component, place)
        }
    }

    private fun putRegisteredComponentIntoPosition(component: Component, position: Int) {
        if(position < 0)
            return
        if (isComponentRegisteredAtPosition(position)) {
            if (isRegisteredComponentAtPositionFillItem(position)) {
                val newPosition = getNextRegisteredPosition(position) ?: return
                changeRegisteredComponentPosition(registeredComponents.filterValues { it == position }.keys.first(), newPosition)
            }else{
                removeRegisteredComponentAtPosition(position)
            }
        }
        registeredComponents[component] = position
        refreshInventory()
    }

    private fun isComponentRegistered(component: Component) = registeredComponents.containsKey(component)
    private fun isComponentRegisteredAtPosition(position: Int) = registeredComponents.values.contains(position)

    private fun changeRegisteredComponentPosition(component: Component, newPosition: Int) {
        val componentAtPosition = getRegisteredComponentAtPosition(newPosition)

        if (componentAtPosition != null)
            registeredComponents.remove(componentAtPosition)

        registeredComponents[component] = newPosition

        if (componentAtPosition != null) {
            val freePlace = getNextRegisteredPosition(newPosition) ?: return
            putRegisteredComponentIntoPosition(componentAtPosition, freePlace)
        }
    }
    private fun changeOutRegisteredComponent(oldComponent: Component, newComponent: Component) {
        if(isComponentRegistered(oldComponent)) {
            val place = registeredComponents[oldComponent] ?: return
            removeRegisteredComponent(oldComponent)
            registeredComponents[newComponent] = place
        }
    }

    private fun isRegisteredComponentFixed(component: Component): Boolean = component.place >= 0
    private fun isRegisteredComponentAtPositionFixed(position: Int): Boolean {
        val component = getRegisteredComponentAtPosition(position) ?: return false
        return isRegisteredComponentFixed(component)
    }
    private fun isRegisteredComponentAtPositionFillItem(position: Int): Boolean =
        isComponentRegisteredAtPosition(position)
                && (!getRegisteredComponentAtPosition(position)!!.meta.savedObjects.containsKey(FILL_ITEM)
                    || getRegisteredComponentAtPosition(position)!!.meta.savedObjects[FILL_ITEM] == false)

    private fun getRegisteredComponentAtPosition(position: Int): Component? = registeredComponents.filterValues { it == position }.keys.firstOrNull()

    private fun removeRegisteredComponent(component: Component) = registeredComponents.remove(component)
    private fun removeRegisteredComponentAtPosition(position: Int) {
        val component = getRegisteredComponentAtPosition(position) ?: return
        removeRegisteredComponent(component)
    }
    private fun removeRegisteredComponents(startPosition: Int, endPosition: Int) {
        for(position in startPosition..endPosition) {
            removeRegisteredComponentAtPosition(position)
        }
    }
    private fun removeRegisteredComponents(components: Array<Component>) {
        for(component in components) {
            removeRegisteredComponent(component)
        }
    }

    private fun getFreeRegisteredPlace(): Int {
        val size = if(forcedSize > 0) forcedSize else MAX_GUI_SIZE
        for(i in 0 until size) {
            if(!registeredComponents.containsValue(i))
                return i
        }
        return -1
    }

    private fun getNextRegisteredPosition(fromPosition: Int): Int? {
        val size = if(forcedSize > 0) forcedSize else MAX_GUI_SIZE
        for(i in fromPosition+1 until size) {
            if(!isRegisteredComponentAtPositionFixed(i))
                return i
        }
        return null
    }

    private fun getRegisteredPositionOfComponent(component: Component): Int {
        if(registeredComponents.containsKey(component))
            return registeredComponents[component] ?: -1
        return -1
    }

    private fun clearRegisteredComponents() {
        registeredComponents.keys.forEach { it.finalizeComponent() }
        registeredComponents.clear()
    }

    fun positionOffsetFromPosition(startPosition: Int, offset: Int) {
        @Suppress("UNCHECKED_CAST")
        (registeredComponents.clone() as HashMap<Component, Int>).forEach { (component, place) ->
            if(place >= startPosition) {
                changeRegisteredComponentPosition(component, place + offset)
            }
        }
        //refreshGuiPositions()
        refreshInventory()
    }


    //endregion


    //region RegisteredFillers

    private fun getRegisteredFillerAtPosition(position: Int): Component? = registeredFillers.filterValues { it == position }.keys.firstOrNull()

    private fun isFillerRegistered(component: Component) = registeredFillers.containsKey(component)
    private fun isFillerRegisteredAtPosition(position: Int) = registeredFillers.values.contains(position)

    private fun getRegisteredPositionOfFiller(component: Component): Int {
        if(registeredFillers.containsKey(component))
            return registeredFillers[component] ?: -1
        return -1
    }

    private fun clearFillers() {
        registeredFillers.keys.forEach { it.finalizeComponent() }
        registeredFillers.clear()
    }

    //endregion

    //endregion



    //region Create/Refresh Gui

    fun openGui(player: Player) {
        openedPlayer = player
        refreshInventory()
    }

    fun refreshInventory() {
        if(openedPlayer == null)
            return

        val slots: Int = getSlotCount()
        if(slots <= 0 || slots > MAX_GUI_SIZE) {
            Bukkit.getServer().logger.severe("There are problems with the slot-size of Gui $title. You can't create a Gui with a SlotSize of $slots. The size must be between 1 and 54")
            throw GuiCreateException()
        }

        var newInventory = false
        if(inventory?.size != slots) {
            inventory = Bukkit.createInventory(openedPlayer, slots, title)
            newInventory = true
        }

        setFillers(slots)

        registeredFillers.forEach { (filler, position) ->
            if(position <= inventory!!.size)
                inventory!!.setItem(position, filler.getGuiItem())
        }
        registeredComponents.forEach { (component, position) ->
            if(position <= inventory!!.size)
                inventory!!.setItem(position, component.getGuiItem())
        }

        if(newInventory)
            openedPlayer?.openInventory(inventory!!)
        else
            openedPlayer?.updateInventory()

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
        val highestPos = registeredComponents.values.maxOf { it }
        return if(highestPos <= MAX_GUI_SIZE) highestPos else MAX_GUI_SIZE
    }

    private fun setFillers(slotSize: Int) {
        if(fillEmptyPlaces) {
            @Suppress("UNCHECKED_CAST")
            (registeredFillers.clone() as HashMap<Component, Int>).forEach { (filler, place) ->
                if(place >= slotSize) {
                    filler.finalizeComponent()
                    registeredFillers.remove(filler)
                }
            }
            for(i in 0 until slotSize) {
                if(!registeredComponents.values.contains(i) && !registeredFillers.containsValue(i)) {
                    registeredFillers[FreeSpaceComponent(ComponentMeta(" ", ItemStack(fillItem)).apply {
                        savedObjects[FILL_ITEM] = true
                    })] = i
                }else if(getRegisteredComponentAtPosition(i) is FreeSpaceComponent) {
                    registeredComponents.filterValues { it == i }.keys.firstOrNull()?.meta = ComponentMeta(" ", ItemStack(fillItem))
                }else if(registeredComponents.containsValue(i) && registeredFillers.containsValue(i)) {
                    val filler = getRegisteredFillerAtPosition(i) ?: continue
                    filler.finalizeComponent()
                    registeredFillers.remove(filler)
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
        registeredComponents.keys.forEach { component ->
            component.finalizeComponent()
        }
        registeredFillers.keys.forEach { filler ->
            filler.finalizeComponent()
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