package io.github.parzivalExe.guiApi.events;

import io.github.parzivalExe.guiApi.Gui;
import io.github.parzivalExe.guiApi.components.Component;
import io.github.parzivalExe.guiApi.components.EventComponent;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

@SuppressWarnings("unused")
public class EventComponentClickedEvent extends ComponentClickedEvent {


    public EventComponentClickedEvent(Component clickedComponent, HumanEntity whoClicked, Gui gui, InventoryAction action, int slot, ClickType clickType) {
        super(clickedComponent, whoClicked, gui, action, slot, clickType);
    }


    //<-------------------------------------------------------------------------/*NEEDED PATH*/------------------------------------------------------------------------>//

    public static HandlerList handlerList = new HandlerList();


    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

}
