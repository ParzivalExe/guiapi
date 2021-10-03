package io.github.parzivalExe.guiApi.events;

import io.github.parzivalExe.guiApi.Gui;
import io.github.parzivalExe.guiApi.components.AdditionalOptionsComponent;
import io.github.parzivalExe.guiApi.components.OpenOption;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

@SuppressWarnings("unused")
public class ExpandAdditionalOptionsEvent extends ComponentClickedEvent {

    final private boolean isExpanded;
    final private OpenOption openOption;


    public ExpandAdditionalOptionsEvent(AdditionalOptionsComponent clickedComponent, HumanEntity whoClicked, Gui gui, InventoryAction action, int slot, ClickType clickType, boolean isExpanded, OpenOption openOption) {
        super(clickedComponent, whoClicked, gui, action, slot, clickType);
        this.isExpanded = isExpanded;
        this.openOption = openOption;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public OpenOption getOpenOption() {
        return openOption;
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
