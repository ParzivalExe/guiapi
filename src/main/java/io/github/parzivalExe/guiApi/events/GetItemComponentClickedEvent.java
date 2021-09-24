package io.github.parzivalExe.guiApi.events;

import io.github.parzivalExe.guiApi.Gui;
import io.github.parzivalExe.guiApi.components.GetItemComponent;
import io.github.parzivalExe.guiApi.objects.InvItemStack;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

import java.util.ArrayList;

public class GetItemComponentClickedEvent extends ComponentClickedEvent {
    private final InvItemStack[] givenItems;

    public GetItemComponentClickedEvent(GetItemComponent getItemComponent, HumanEntity whoClicked, Gui gui, InventoryAction action, int slot, ClickType clickType, InvItemStack[] givenItems) {
        super(getItemComponent, whoClicked, gui, action, slot, clickType);
        this.givenItems = givenItems;
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
