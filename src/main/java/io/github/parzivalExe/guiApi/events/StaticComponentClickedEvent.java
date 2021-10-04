package io.github.parzivalExe.guiApi.events;

import io.github.parzivalExe.guiApi.Gui;
import io.github.parzivalExe.guiApi.components.StaticComponent;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

@SuppressWarnings("ALL")
public class StaticComponentClickedEvent extends ComponentClickedEvent {


    public StaticComponentClickedEvent(StaticComponent clickedStaticComponent, HumanEntity whoClicked, Gui clickedGui, InventoryAction action, int slot, ClickType clickType) {
        super(clickedStaticComponent, whoClicked, clickedGui, action, slot, clickType);
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
