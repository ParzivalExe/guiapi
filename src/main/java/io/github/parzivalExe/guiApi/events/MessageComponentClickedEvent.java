package io.github.parzivalExe.guiApi.events;

import io.github.parzivalExe.guiApi.Gui;
import io.github.parzivalExe.guiApi.components.MessageComponent;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

@SuppressWarnings("unused")
public class MessageComponentClickedEvent extends Event {

    private final MessageComponent clickedComponent;
    private final HumanEntity whoClicked;
    private final Gui gui;
    private final InventoryAction action;
    private final int slot;
    private final ClickType clickType;


    public MessageComponentClickedEvent(MessageComponent clickedComponent, HumanEntity whoClicked, Gui gui, InventoryAction action, int slot, ClickType clickType) {

        this.clickedComponent = clickedComponent;
        this.whoClicked = whoClicked;
        this.gui = gui;
        this.action = action;
        this.slot = slot;
        this.clickType = clickType;

    }



    public MessageComponent getClickedComponent() {
        return this.clickedComponent;
    }

    public HumanEntity getWhoClicked() {
        return this.whoClicked;
    }

    public Gui getGui() {
        return this.gui;
    }

    public InventoryAction getAction() {
        return this.action;
    }

    public int getSlot() {
        return this.slot;
    }

    public ClickType getClickType() {
        return this.clickType;
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
