package io.github.parzivalExe.guiApi.components

import io.github.parzivalExe.guiApi.Gui
import io.github.parzivalExe.guiApi.antlr.converter.OpenOptionConverter
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLAttribute
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLContent
import io.github.parzivalExe.guiApi.events.ExpandAdditionalOptionsEvent
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.inventory.ItemStack
import kotlin.math.ceil

@Suppress("unused")
open class AdditionalOptionsComponent(meta: ComponentMeta, @XMLContent val additionalComponents: ArrayList<Component>)
    : Component(meta) {

    companion object {
        const val KEY_ORIGINAL_PLACE = "originalPlace"
    }

    @XMLAttribute
    var newInvTitle = "?"

    @XMLAttribute(converter = OpenOptionConverter::class)
    var openOption = OpenOption.UNDER_INVENTORY

    protected var isOpened = false


    internal constructor() : this(ComponentMeta("", ItemStack(Material.WHITE_WOOL)))
    constructor(meta: ComponentMeta) : this(meta, arrayListOf())


    override fun finalizeComponent() {
        super.finalizeComponent()
        if(openOption != OpenOption.NEW_INVENTORY || !isOpened) {
            for (additionalComponent in additionalComponents) {
                additionalComponent.finalizeComponent()
            }
        }
    }


    override fun componentClicked(whoClicked: HumanEntity, gui: Gui, action: InventoryAction, slot: Int, clickType: ClickType) {
        when(openOption) {
            OpenOption.UNDER_INVENTORY -> openUnderInventory(whoClicked, gui, action, clickType)
            OpenOption.NEW_INVENTORY -> openNewInventory(whoClicked, gui, action, clickType)
        }
    }

    internal fun openUnderInventory(whoClicked: HumanEntity, gui: Gui, action: InventoryAction, clickType: ClickType) {
        val startPosition = (gui.getPositionOfComponent(this) / 9 + 1) * 9
        if(!isOpened) {
            gui.positionOffsetFromPosition(startPosition, getOffset())
            for ((index, component) in additionalComponents.withIndex()) {
                if(component.place >= 0) {
                    component.meta.savedObjects[KEY_ORIGINAL_PLACE] = component.place
                    component.place = startPosition + component.place
                } else {
                    component.meta.savedObjects[KEY_ORIGINAL_PLACE] = component.place
                    component.place = startPosition + index
                }
                gui.addComponent(component)
            }
        }else{
            additionalComponents.forEachIndexed { _, component ->
                if(component is AdditionalOptionsComponent && component.isOpened)
                    component.openUnderInventory(whoClicked, gui, action, clickType)

                gui.removeComponent(component)
                component.place = component.meta.savedObjects[KEY_ORIGINAL_PLACE] as Int
                component.meta.savedObjects.remove(KEY_ORIGINAL_PLACE)
            }
            gui.positionOffsetFromPosition(startPosition, -getOffset())
        }

        isOpened = !isOpened
        Bukkit.getPluginManager().callEvent(
            ExpandAdditionalOptionsEvent(this, whoClicked, gui, action, place, clickType, isOpened, openOption))
    }

    @Suppress("MemberVisibilityCanBePrivate")
    internal fun openNewInventory(whoClicked: HumanEntity, gui: Gui, action: InventoryAction, clickType: ClickType) {
        if(gui.openedPlayer == null)
            return
        val newGui = Gui(if(newInvTitle == "?") meta.title else newInvTitle)
        additionalComponents.forEach { newGui.addComponent(it) }
        isOpened = true
        newGui.openGui(gui.openedPlayer!!, gui)
        Bukkit.getPluginManager().callEvent(ExpandAdditionalOptionsEvent(this, whoClicked, gui, action, place, clickType, isOpened, openOption))
    }

    private fun getOffset(): Int {
        val offset = ceil(additionalComponents.count()/9.0).toInt()*9
        val maxPlace = additionalComponents.maxOf { component ->
            if(component.meta.savedObjects.containsKey(KEY_ORIGINAL_PLACE))
                return@maxOf component.meta.savedObjects[KEY_ORIGINAL_PLACE] as Int
            else
                return@maxOf component.place
        }
        if(maxPlace >= offset)
            return ceil((maxPlace+1)/9.0).toInt()*9
        return offset
    }

}