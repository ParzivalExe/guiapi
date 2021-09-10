package io.github.parzivalExe.guiApi.components

import io.github.parzivalExe.guiApi.Gui
import io.github.parzivalExe.guiApi.antlr.converter.ItemStackConverter
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLAttribute
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLConstructor
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.inventory.ItemStack

@Suppress("MemberVisibilityCanBePrivate")
abstract class Component(componentMeta: ComponentMeta) {

    @XMLConstructor([
        XMLAttribute(necessary = true, attrName = "title", defaultValue = ""),
        XMLAttribute(necessary = true, attrName = "look", defaultValue = "35", converter = ItemStackConverter::class),
        XMLAttribute(attrName = "description", defaultValue = "[]"/*, converter = StringArrayListConverter::class*/)
    ])
    var meta = componentMeta
        set(value) {
            look = value.buildItem()
            field = value
        }
    var look = meta.buildItem()
        private set

    //@XMLAttribute(defaultValue = "", converter = StringToStringArrayConverter::class)
    var description: ArrayList<String>
        set(value) {
            meta.description = value
        }
        get() = meta.description

    var id: Int = ComponentManager.getNewComponentId()
        private set
    var clickAction: ComponentClickAction? = null

    @XMLAttribute(defaultValue = "-1")
    var place = -1



    init {
        @Suppress("LeakingThis")
        ComponentManager.initializeComponent(this)
    }

    //region Finalization

    open fun finalizeComponent() {
        ComponentManager.finalizeComponent(this)
    }

    //endregion


    open fun getGuiItem(): ItemStack {
        return look
    }

    //region ClickAction

    open fun startClickAction(whoClicked: Player, gui: Gui, action: InventoryAction, clickType: ClickType): Boolean {
        if(clickAction == null)
            return false
        return clickAction!!.onClick(this, whoClicked, gui, action, clickType)
    }

    //endregion

    //region AbstractMethods

    abstract fun componentClicked(whoClicked: HumanEntity, gui: Gui, action: InventoryAction, slot: Int, clickType: ClickType)

    //endregion


    override fun equals(other: Any?): Boolean {
        if(other is Component) {
            return other.id == this.id
        }
        return false
    }


}