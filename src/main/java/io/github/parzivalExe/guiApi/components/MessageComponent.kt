package io.github.parzivalExe.guiApi.components

import io.github.parzivalExe.guiApi.Gui
import io.github.parzivalExe.guiApi.events.MessageComponentClickedEvent
import io.github.parzivalExe.objectXmlParser.IXmlTag
import io.github.parzivalExe.objectXmlParser.XMLAttribute
import io.github.parzivalExe.objectXmlParser.XMLTag
import org.bukkit.Bukkit
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction

class MessageComponent(meta: ComponentMeta) : Component(meta), IXmlTag {

    @XMLAttribute
    var message: String = ""

    companion object {
        @Suppress("unused")
        @JvmStatic
        fun initializeInstance(xmlTag: XMLTag): Any {
            return MessageComponent(xmlTag.getXmlAttributeByName("meta")!!.getConvertedValue() as ComponentMeta)
        }
    }

    override fun componentClicked(whoClicked: HumanEntity, gui: Gui, action: InventoryAction, slot: Int, clickType: ClickType) {
        if(whoClicked is Player && message != null) {
            whoClicked.sendMessage(message)
            gui.closeGui()
            Bukkit.getPluginManager().callEvent(MessageComponentClickedEvent(this, whoClicked, gui, action, slot, clickType))
        }
    }

}