package io.github.parzivalExe.guiApi.events;

import io.github.parzivalExe.guiApi.Gui;
import io.github.parzivalExe.guiApi.components.StaticComponent;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

@SuppressWarnings("ALL")
public class StaticComponentClickedEvent extends ComponentClickedEvent {

    public StaticComponentClickedEvent(StaticComponent clickedStaticComponent, HumanEntity whoClicked, Gui clickedGui, InventoryAction action, int slot, ClickType clickType) {
        super(clickedStaticComponent, whoClicked, clickedGui, action, slot, clickType);
    }

}
