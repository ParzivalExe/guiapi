package io.github.parzivalExe.guiApi.events;

import io.github.parzivalExe.guiApi.Gui;
import io.github.parzivalExe.guiApi.components.AdditionalOptionsComponent;
import io.github.parzivalExe.guiApi.components.OpenOption;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

@SuppressWarnings("unused")
public class ExpandAdditionalOptionsEvent extends Event {

    private final AdditionalOptionsComponent clickedComponent;
    private final HumanEntity whoClicked;
    private final Gui gui;
    final private InventoryAction action;
    final private int slot;
    final private ClickType clickType;
    final private boolean isExpanded;
    final private OpenOption openOption;


    public ExpandAdditionalOptionsEvent(AdditionalOptionsComponent clickedComponent, HumanEntity whoClicked, Gui gui, InventoryAction action, int slot, ClickType clickType, boolean isExpanded, OpenOption openOption) {
        this.clickedComponent = clickedComponent;
        this.whoClicked = whoClicked;
        this.gui = gui;
        this.action = action;
        this.slot = slot;
        this.clickType = clickType;
        this.isExpanded = isExpanded;
        this.openOption = openOption;
    }

    public AdditionalOptionsComponent getClickedComponent() {
        return clickedComponent;
    }

    public ClickType getClickType() {
        return clickType;
    }

    public Gui getGui() {
        return gui;
    }

    public HumanEntity getWhoClicked() {
        return whoClicked;
    }

    public int getSlot() {
        return slot;
    }

    public InventoryAction getAction() {
        return action;
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
