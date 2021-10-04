package io.github.parzivalExe.guiApi.events;

import io.github.parzivalExe.guiApi.Gui;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GuiCloseEvent extends Event {

    private final Gui closedGui;
    private final Player player;

    public GuiCloseEvent(Gui closedGui, Player player) {
        this.closedGui = closedGui;
        this.player = player;
    }

    public Gui getClosedGui() {
        return closedGui;
    }

    public Player getPlayer() {
        return player;
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
