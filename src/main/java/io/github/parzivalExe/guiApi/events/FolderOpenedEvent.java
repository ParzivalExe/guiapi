package io.github.parzivalExe.guiApi.events;

import io.github.parzivalExe.guiApi.Gui;
import io.github.parzivalExe.guiApi.components.Folder;
import io.github.parzivalExe.guiApi.components.OpenOption;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

public class FolderOpenedEvent extends Event {

    private final Folder openedFolder;
    private final HumanEntity whoClicked;
    private final Gui gui;
    private final InventoryAction action;
    private final int slot;
    private final ClickType clickType;
    private final Gui folderOpenedGui;

    public FolderOpenedEvent(Folder openedFolder, HumanEntity whoClicked, Gui gui, InventoryAction action, int slot, ClickType clickType, Gui folderOpenedGui) {
        this.openedFolder = openedFolder;
        this.whoClicked = whoClicked;
        this.gui = gui;
        this.action = action;
        this.slot = slot;
        this.clickType = clickType;
        this.folderOpenedGui = folderOpenedGui;
    }

    public Folder getOpenedFolder() {
        return openedFolder;
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

    public int getSlot() {
        return slot;
    }

    public ClickType getClickType() {
        return clickType;
    }

    public Gui getFolderOpenedGui() {
        return folderOpenedGui;
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
