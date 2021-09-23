package io.github.parzivalExe.guiApi.events;

import io.github.parzivalExe.guiApi.Gui;
import io.github.parzivalExe.guiApi.components.GetItemComponent;
import io.github.parzivalExe.guiApi.objects.InvItemStack;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryAction;

import java.util.ArrayList;

public class GetItemComponentEvent extends Event {

    private final GetItemComponent getItemComponent;
    private final HumanEntity whoClicked;
    private final Gui gui;
    private final InventoryAction action;
    private final int slot;
    private final InvItemStack[] givenItems;

    public GetItemComponentEvent(GetItemComponent getItemComponent, HumanEntity whoClicked, Gui gui, InventoryAction action, int slot, InvItemStack[] givenItems) {
        this.getItemComponent = getItemComponent;
        this.whoClicked = whoClicked;
        this.gui = gui;
        this.action = action;
        this.slot = slot;
        this.givenItems = givenItems;
    }

    public GetItemComponent getGetItemComponent() {
        return getItemComponent;
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

    public InvItemStack[] getGivenItems() {
        return givenItems;
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
