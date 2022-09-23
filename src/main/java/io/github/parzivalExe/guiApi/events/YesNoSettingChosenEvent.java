package io.github.parzivalExe.guiApi.events;

import io.github.parzivalExe.guiApi.Gui;
import io.github.parzivalExe.guiApi.components.YesNoSetting;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

public class YesNoSettingChosenEvent extends ComponentClickedEvent {

    private final boolean yesSelected;

    public YesNoSettingChosenEvent(YesNoSetting componentClicked, boolean yesSelected, HumanEntity whoClicked, Gui gui, InventoryAction action, int slot, ClickType clickType) {
        super(componentClicked, whoClicked, gui, action, slot, clickType);
        this.yesSelected = yesSelected;
    }

    public boolean isYesSelected() {
        return yesSelected;
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