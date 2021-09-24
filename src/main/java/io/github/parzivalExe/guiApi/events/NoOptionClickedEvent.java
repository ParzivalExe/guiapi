package io.github.parzivalExe.guiApi.events;

import io.github.parzivalExe.guiApi.Gui;
import io.github.parzivalExe.guiApi.components.StaticComponent;
import io.github.parzivalExe.guiApi.components.YesNoOption;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

@SuppressWarnings("unused")
public class NoOptionClickedEvent extends ComponentClickedEvent {

    private final StaticComponent noOptionComponent;


    public NoOptionClickedEvent(YesNoOption yesNoOption, StaticComponent noOptionComponent, Gui gui, Player whoClicked, InventoryAction action, int slot, ClickType clickType) {
        super(yesNoOption, whoClicked, gui, action, slot, clickType);
        this.noOptionComponent = noOptionComponent;
    }

    public StaticComponent getNoOptionComponent() {
        return this.noOptionComponent;
    }


    //<------------------------------------------------------------------/*NEEDED PATH*/------------------------------------------------------------>//

    public static HandlerList handlerList = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }


}
