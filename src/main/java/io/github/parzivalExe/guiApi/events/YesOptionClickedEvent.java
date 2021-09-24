package io.github.parzivalExe.guiApi.events;

import io.github.parzivalExe.guiApi.Gui;
import io.github.parzivalExe.guiApi.components.StaticComponent;
import io.github.parzivalExe.guiApi.components.YesNoOption;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

@SuppressWarnings("unused")
public class YesOptionClickedEvent extends ComponentClickedEvent {

    private final StaticComponent yesOptionComponent;


    public YesOptionClickedEvent(YesNoOption yesNoOption, StaticComponent yesOptionComponent, Gui gui, Player whoClicked, InventoryAction action, int slot, ClickType clickType) {
        super(yesNoOption, whoClicked, gui, action, slot, clickType);
        this.yesOptionComponent = yesOptionComponent;

    }

    public StaticComponent getYesOptionComponent() {
        return this.yesOptionComponent;
    }


    //<-------------------------------------------------------------------------/*NEEDED PATH*/-------------------------------------------------------------->//

    public static HandlerList handlerList = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }


}
