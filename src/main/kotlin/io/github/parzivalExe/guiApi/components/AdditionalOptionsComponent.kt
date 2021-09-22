package io.github.parzivalExe.guiApi.components

import io.github.parzivalExe.guiApi.Gui
import io.github.parzivalExe.guiApi.antlr.converter.OpenOptionConverter
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLAttribute
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLContent
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.inventory.ItemStack
import kotlin.math.ceil

@Suppress("unused")
open class AdditionalOptionsComponent(@XMLContent(necessary = true) val additionalComponents: ArrayList<Component>, meta: ComponentMeta) : Component(meta) {

    @XMLAttribute
    var newInvTitle = "?"

    @XMLAttribute(converter = OpenOptionConverter::class)
    var openOption = OpenOption.UNDER_INVENTORY

    protected var isOpened = false


    constructor() : this(arrayListOf<Component>(), ComponentMeta("", ItemStack(Material.WHITE_WOOL)))
    constructor(meta: ComponentMeta) : this(arrayListOf(), meta)


    override fun finalizeComponent() {
        super.finalizeComponent()
        for(additionalComponent in additionalComponents) {
            @Suppress("UNNECESSARY_SAFE_CALL")
            additionalComponent?.finalizeComponent()
        }
    }


    override fun componentClicked(whoClicked: HumanEntity, gui: Gui, action: InventoryAction, slot: Int, clickType: ClickType) {
        when(openOption) {
            OpenOption.UNDER_INVENTORY -> openUnderInventory(gui)
            OpenOption.NEW_INVENTORY -> openNewInventory(gui)
        }
    }

    internal fun openUnderInventory(gui: Gui) {
        val startPosition = (gui.getPositionOfComponent(this) / 9 + 1) * 9
        if(!isOpened) {
            gui.positionOffsetFromPosition(startPosition, ceil(additionalComponents.count()/9.0).toInt()*9)
            for ((index, component) in additionalComponents.withIndex()) {
                if(component.place >= 0)
                    component.place = startPosition + index + component.place
                else
                    component.place = startPosition + index
                gui.addComponent(component)
            }
        }else{
            additionalComponents.forEachIndexed { index, component ->
                if(component is AdditionalOptionsComponent && component.isOpened)
                    component.openUnderInventory(gui)

                gui.removeComponent(component)
                component.place = component.place - startPosition - index
            }
            gui.positionOffsetFromPosition(startPosition, -ceil(additionalComponents.count()/9.0).toInt()*9)
        }

        isOpened = !isOpened
    }

    @Suppress("MemberVisibilityCanBePrivate")
    internal fun openNewInventory(gui: Gui) {
        if(gui.openedPlayer == null)
            return
        val newGui = Gui(if(newInvTitle == "?") meta.title else newInvTitle)
        additionalComponents.forEach { newGui.addComponent(it) }
        newGui.openGui(gui.openedPlayer!!)
        isOpened = true
    }


}