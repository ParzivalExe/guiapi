package io.github.parzivalExe.guiApi.events;

import io.github.parzivalExe.guiApi.Gui;
import io.github.parzivalExe.guiApi.components.TeleportComponent;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

@SuppressWarnings("unused")
public class TeleportComponentClickedEvent extends Event {

    private final TeleportComponent clickedComponent;
    private final HumanEntity whoClicked;
    private final Gui gui;
    private final InventoryAction action;
    private final int slot;
    private final ClickType clickType;

    public TeleportComponentClickedEvent(TeleportComponent clickedComponent, HumanEntity whoClicked, Gui gui, InventoryAction action, int slot, ClickType clickType) {
        this.clickedComponent = clickedComponent;
        this.whoClicked = whoClicked;
        this.gui = gui;
        this.action = action;
        this.slot = slot;
        this.clickType = clickType;
    }

    public TeleportComponent getClickedComponent() {
        return clickedComponent;
    }

    public HumanEntity getWhoClicked() {
        return whoClicked;
    }

    public Gui getGui() {
        return gui;
    }

    public InventoryAction getAction() {
        return action;
    }

    public int getSlot() {
        return slot;
    }

    public ClickType getClickType() {
        return clickType;
    }

    //region Handler

    public static HandlerList handlerList = new HandlerList();


    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    //endregion

}
