package io.github.parzivalExe.guiApi.antlr.elements

import io.github.parzivalExe.guiApi.Gui
import io.github.parzivalExe.guiApi.antlr.converter.ItemStackConverter
import io.github.parzivalExe.guiApi.components.Component
import org.bukkit.inventory.ItemStack

class GuiElement(tagName: String) : DynamicElement(tagName) {

    override fun createObject(library: Library): Any = createGui(library)

    fun createGui(library: Library): Gui {
        val gui = Gui(getValueForAttribute("title"))
        gui.fillEmptyPlaces = getValueForAttributeOrDefault("fillEmptySpaces", "true").toBoolean()
        gui.fillItem = ItemStackConverter().attributeStringToValue(getValueForAttributeOrDefault("fillItem", "160:7"), gui.fillItem) as ItemStack
        gui.forcedSize = getValueForAttributeOrDefault("forcedSize", "-1").toInt()


        val components = arrayListOf<Component>()
        //val library: Library = (content?.elements?.first { element -> element is LibraryElement } as LibraryElement).CreateLibrary()
        content?.elements?.forEach { componentElement ->
            if(componentElement is DynamicElement) {
                val clazz = componentElement.getCreateObjectClass(library)
                if(clazz != null && Component::class.java.isAssignableFrom(clazz)) {
                    val component = (componentElement.createObject(library) ?: return@forEach) as Component
                    components.add(component)
                }
            }
        }
        components.forEach { component -> gui.addComponent(component) }

        return gui
    }

}