package io.github.parzivalExe.guiApi.events;

import io.github.parzivalExe.guiApi.Gui;
import io.github.parzivalExe.guiApi.components.Folder;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

public class FolderOpenedEvent extends ComponentClickedEvent {

    private final Gui folderOpenedGui;

    public FolderOpenedEvent(Folder openedFolder, HumanEntity whoClicked, Gui gui, InventoryAction action, int slot, ClickType clickType, Gui folderOpenedGui) {
        super(openedFolder, whoClicked, gui, action, slot, clickType);
        this.folderOpenedGui = folderOpenedGui;
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
