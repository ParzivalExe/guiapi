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
        XMLAttribute(attrName = "title", defaultValue = ""),
        XMLAttribute(attrName = "look", defaultValue = "35", converter = ItemStackConverter::class),
        XMLAttribute(attrName = "description")
    ])
    open var meta = componentMeta


    var id: Int = ComponentManager.getNewComponentId()
        private set
    var clickActions = arrayListOf<ComponentClickAction>()
        private set

    @XMLAttribute
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


    open fun getGuiItem(): ItemStack = meta.buildItem()

    //region ClickAction

    open fun startClickAction(whoClicked: Player, gui: Gui, action: InventoryAction, clickType: ClickType): Boolean {
        var handled = false
        clickActions.forEach { if(it.onClick(this, whoClicked, gui, action, clickType)) handled = true }
        return handled
    }

    fun addClickListener(clickListener: ComponentClickAction) = clickActions.add(clickListener)

    fun removeClickListener(clickListener: ComponentClickAction): Boolean = clickActions.remove(clickListener)

    fun hasRegisteredClickListener(clickListener: ComponentClickAction): Boolean = clickActions.contains(clickListener)

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

    override fun hashCode(): Int = id

    override fun toString(): String =
        //"${javaClass.simpleName} [$id]:\n  title: ${meta.title}\n  look: ${meta.buildItem()}\n  place: $place"
        "${javaClass.simpleName} [$id]:\n  title: ${meta.title}\n  look: ${meta.buildItem()}\n  place: $place"

}