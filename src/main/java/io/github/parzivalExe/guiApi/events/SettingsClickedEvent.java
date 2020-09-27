package io.github.parzivalExe.guiApi.events;

import io.github.parzivalExe.guiApi.Gui;
import io.github.parzivalExe.guiApi.components.SettingOption;
import io.github.parzivalExe.guiApi.components.Settings;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

@SuppressWarnings("unused")
public class SettingsClickedEvent extends Event {

    private final Settings clickedSettings;
    private final HumanEntity whoClicked;
    private final Gui clickedGui;
    private final InventoryAction action;
    private final int slot;
    private final ClickType clickType;
    private final SettingOption clickedOption;
    private final SettingOption newSetting;

    public SettingsClickedEvent(Settings clickedSettings, SettingOption clickedOption, SettingOption newSetting, HumanEntity whoClicked, Gui clickedGui, InventoryAction action, int slot, ClickType clickType) {
        this.clickedSettings = clickedSettings;
        this.whoClicked = whoClicked;
        this.clickedGui = clickedGui;
        this.action = action;
        this.slot = slot;
        this.clickType = clickType;
        this.clickedOption = clickedOption;
        this.newSetting = newSetting;

    }

    public Settings getClickedSettings() {
        return clickedSettings;
    }
    public HumanEntity getWhoClicked() {
        return whoClicked;
    }
    public Gui getClickedGui() {
        return clickedGui;
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
    public SettingOption getClickedOption() {
        return clickedOption;
    }
    public SettingOption getNewSetting() {
        return newSetting;
    }



    //<-------------------------------------------------------------/*NEEDED METHODS*/--------------------------------------------------->//

    public static HandlerList handlerList = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

}
