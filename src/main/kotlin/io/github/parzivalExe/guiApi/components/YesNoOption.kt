package io.github.parzivalExe.guiApi.components

import io.github.parzivalExe.guiApi.Gui
import io.github.parzivalExe.guiApi.antlr.converter.ItemStackConverter
import io.github.parzivalExe.guiApi.antlr.converter.OpenOptionConverter
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLAttribute
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLConstructor
import io.github.parzivalExe.guiApi.events.NoOptionClickedEvent
import io.github.parzivalExe.guiApi.events.YesOptionClickedEvent
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.inventory.ItemStack

class YesNoOption(meta: ComponentMeta) : Component(meta), ComponentClickAction {

    companion object {
        const val YES_NO_OPTION_KEY = "yesNoComponent"
    }

    constructor() : this(ComponentMeta("", ItemStack(Material.WOOL)))


    @XMLAttribute(attrName = "dialogTitle", defaultValue = "Yes or No")
    var yesNoDialogTitle = "Yes or No"

    @XMLConstructor([XMLAttribute(attrName = "yesTitle", defaultValue = "YES"), XMLAttribute(attrName = "yesLook", defaultValue = "35:5", converter = ItemStackConverter::class)])
    var yesMeta = ComponentMeta("YES", ItemStack(35, 1, 0, 5))

    var yesOption = StaticComponent(yesMeta)
        private set
        get() {
            yesMeta.savedObjects[YES_NO_OPTION_KEY] = this
            return StaticComponent(yesMeta)
        }

    @XMLConstructor([XMLAttribute(attrName = "noTitle", defaultValue = "no"), XMLAttribute(attrName = "noLook", defaultValue = "166", converter = ItemStackConverter::class)])
    var noMeta = ComponentMeta("no", ItemStack(Material.BARRIER))

    var noOption = StaticComponent(noMeta)
        private set
        get() {
            noMeta.savedObjects[YES_NO_OPTION_KEY] = this
            return StaticComponent(noMeta)
        }

    var isOpened = false
    private var newWindowOpening = false

    @XMLAttribute(defaultValue = "underInventory", converter = OpenOptionConverter::class)
    var openOption = OpenOption.UNDER_INVENTORY



    @Suppress("unused")
    constructor(meta: ComponentMeta, yesNoDialogTitle: String, yesMeta: ComponentMeta, noMeta: ComponentMeta, openOption: OpenOption) : this(meta) {
        this.yesNoDialogTitle = yesNoDialogTitle
        this.yesMeta = yesMeta
        this.noMeta = noMeta
        this.openOption = openOption
    }

    override fun finalizeComponent() {
        super.finalizeComponent()
        if(!newWindowOpening) {
            yesOption.finalizeComponent()
            noOption.finalizeComponent()
        }
        isOpened = false
    }

    override fun componentClicked(whoClicked: HumanEntity, gui: Gui, action: InventoryAction, slot: Int, clickType: ClickType) {
        if(whoClicked is Player) {
            if (openOption == OpenOption.UNDER_INVENTORY) {
                openYesNoOptionUnderGui(whoClicked, gui)
            } else if (openOption == OpenOption.NEW_INVENTORY) {
                openYesNoOptionInNewGui(whoClicked)
            }
        }
    }

    override fun onClick(component: Component, whoClicked: Player, gui: Gui, action: InventoryAction, clickType: ClickType): Boolean {
        if(component == yesOption) {
            if(openOption == OpenOption.UNDER_INVENTORY)
                openYesNoOptionUnderGui(whoClicked, gui)
            else
                gui.closeGui()
            Bukkit.getPluginManager().callEvent(YesOptionClickedEvent(yesOption.meta.savedObjects[YES_NO_OPTION_KEY] as YesNoOption, component as StaticComponent, gui, whoClicked, action, component.place, clickType))
            return true
        }else if(component == noOption) {
            if(openOption == OpenOption.UNDER_INVENTORY)
                openYesNoOptionUnderGui(whoClicked, gui)
            else
                gui.closeGui()
            Bukkit.getPluginManager().callEvent(NoOptionClickedEvent(noOption.meta.savedObjects[YES_NO_OPTION_KEY] as YesNoOption, component as StaticComponent, gui, whoClicked, action, component.place, clickType))
            return true
        }
        return false
    }

    fun openYesNoOptionUnderGui(player: Player, gui: Gui) {
        if(!isOpened) {
            /*OPEN UNDER INV*/
            val size = gui.getSlotCount()

            if(size+8 > Gui.MAX_GUI_SIZE) {
                /*Gui can't be that big -> open in new Gui is only option*/
                openYesNoOptionInNewGui(player)
            }

            gui.setComponent(yesOption, size+3)
            gui.setComponent(noOption, size+5)
            if(gui.sizeIsForced()) {
                gui.forcedSize += 9
            }

            /*OPEN EDITED GUI*/
            gui.openGui(player)

            isOpened = true
        }else{
            /*CLOSE UNDER INV*/
            if(gui.sizeIsForced()) {
                gui.forcedSize -= 9
            }
            gui.removeComponents(gui.getSlotCount()-9, gui.getSlotCount()-1)

            gui.openGui(player)

            isOpened = false
        }
    }

    fun openYesNoOptionInNewGui(player: Player) {
        newWindowOpening = true
        val yesNoGui = Gui(yesNoDialogTitle)

        yesNoGui.setComponent(yesOption, 3)
        yesNoGui.setComponent(noOption, 5)

        yesNoGui.openGui(player)
        newWindowOpening = false
        isOpened = true
    }



    enum class OpenOption {
        UNDER_INVENTORY, NEW_INVENTORY
    }

}