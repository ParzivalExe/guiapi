package io.github.parzivalExe.guiApi.events;

import io.github.parzivalExe.guiApi.Gui;
import io.github.parzivalExe.guiApi.components.StaticComponent;
import io.github.parzivalExe.guiApi.components.YesNoOption;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

@SuppressWarnings("unused")
public class YesOptionClickedEvent extends Event {


    private final YesNoOption yesNoOption;
    private final StaticComponent yesOptionComponent;
    private final Gui gui;
    private final Player whoClicked;
    private final InventoryAction action;
    private final int slot;
    private final ClickType clickType;


    public YesOptionClickedEvent(YesNoOption yesNoOption, StaticComponent yesOptionComponent, Gui gui, Player whoClicked, InventoryAction action, int slot, ClickType clickType) {

        this.yesNoOption = yesNoOption;
        this.yesOptionComponent = yesOptionComponent;
        this.gui = gui;
        this.whoClicked = whoClicked;
        this.action = action;
        this.slot = slot;
        this.clickType = clickType;

    }


    public YesNoOption getComponent() {
        return this.yesNoOption;
    }

    public StaticComponent getYesOptionComponent() {
        return this.yesOptionComponent;
    }

    public Gui getGui() {
        return this.gui;
    }

    public Player getWhoClicked() {
        return this.whoClicked;
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
