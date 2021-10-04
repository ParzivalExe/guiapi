package io.github.parzivalExe.guiApi.events;

import io.github.parzivalExe.guiApi.Gui;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GuiRefreshEvent extends Event {

    private final Gui refreshedGui;
    private final Player player;

    public GuiRefreshEvent(Gui refreshedGui, Player player) {
        this.refreshedGui = refreshedGui;
        this.player = player;
    }

    public Gui getRefreshedGui() {
        return refreshedGui;
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
