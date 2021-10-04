package io.github.parzivalExe.guiApi.events;

import io.github.parzivalExe.guiApi.Gui;
import io.github.parzivalExe.guiApi.components.TeleportComponent;
import io.github.parzivalExe.guiApi.objects.TeleportLocation;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

@SuppressWarnings("unused")
public class TeleportComponentClickedEvent extends ComponentClickedEvent {

    private final TeleportLocation location;

    public TeleportComponentClickedEvent(TeleportComponent clickedComponent, HumanEntity whoClicked, Gui gui, InventoryAction action, int slot, ClickType clickType, TeleportLocation location) {
        super(clickedComponent, whoClicked, gui, action, slot, clickType);
        this.location = location;
    }

    public TeleportLocation getLocation() {
        return location;
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
