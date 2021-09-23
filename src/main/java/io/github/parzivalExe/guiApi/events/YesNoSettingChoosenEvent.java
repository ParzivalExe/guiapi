package io.github.parzivalExe.guiApi.events;

import io.github.parzivalExe.guiApi.Gui;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

public class YesNoSettingChoosenEvent extends Event {

    private final boolean yesSelected;
    private final HumanEntity whoClicked;
    private final Gui gui;
    private final InventoryAction action;
    private final ClickType clickType;

    public YesNoSettingChoosenEvent(boolean yesSelected, HumanEntity whoClicked, Gui gui, InventoryAction action, ClickType clickType) {
        this.yesSelected = yesSelected;
        this.whoClicked = whoClicked;
        this.gui = gui;
        this.action = action;
        this.clickType = clickType;
    }

    public boolean isYesSelected() {
        return yesSelected;
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

    public ClickType getClickType() {
        return clickType;
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
