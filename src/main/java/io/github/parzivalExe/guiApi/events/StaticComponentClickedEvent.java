package io.github.parzivalExe.guiApi.events;

import io.github.parzivalExe.guiApi.Gui;
import io.github.parzivalExe.guiApi.components.StaticComponent;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

@SuppressWarnings("ALL")
public class StaticComponentClickedEvent extends Event {

    private final HumanEntity whoClicked;
    private final Gui clickedGui;
    private final InventoryAction action;
    private final int slot;
    private final ClickType clickType;
    private final StaticComponent clickedStaticComponent;

    public StaticComponentClickedEvent(StaticComponent clickedStaticComponent, HumanEntity whoClicked, Gui clickedGui, InventoryAction action, int slot, ClickType clickType) {
        this.whoClicked = whoClicked;
        this.clickedGui = clickedGui;
        this.action = action;
        this.slot = slot;
        this.clickType = clickType;
        this.clickedStaticComponent = clickedStaticComponent;
    }

    public HumanEntity getWhoClicked() {
        return this.whoClicked;
    }

    public Gui getClickedGui() {
        return this.clickedGui;
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

    public StaticComponent getClickedStaticComponent() {
        return clickedStaticComponent;
    }


    //region NeededEventMethods
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
