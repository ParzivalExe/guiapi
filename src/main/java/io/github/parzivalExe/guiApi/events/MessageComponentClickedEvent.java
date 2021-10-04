package io.github.parzivalExe.guiApi.events;

import io.github.parzivalExe.guiApi.Gui;
import io.github.parzivalExe.guiApi.components.MessageComponent;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

@SuppressWarnings("unused")
public class MessageComponentClickedEvent extends ComponentClickedEvent {

    private final String message;


    public MessageComponentClickedEvent(MessageComponent clickedComponent, HumanEntity whoClicked, Gui gui, InventoryAction action, int slot, ClickType clickType, String message) {
        super(clickedComponent, whoClicked, gui, action, slot, clickType);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

//<--------------------------------------------------------------------/*NEEDED PATH*/------------------------------------------------------------->//

    public static HandlerList handlerList = new HandlerList();


    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

}
