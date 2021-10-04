package io.github.parzivalExe.guiApi.events;

import io.github.parzivalExe.guiApi.Gui;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GuiOpenEvent extends Event {

    private final Gui openedGui;
    private final Player player;

    public GuiOpenEvent(Gui openedGui, Player player) {
        this.openedGui = openedGui;
        this.player = player;
    }

    public Gui getOpenedGui() {
        return openedGui;
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
