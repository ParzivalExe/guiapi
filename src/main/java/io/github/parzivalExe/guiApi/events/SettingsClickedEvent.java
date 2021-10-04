package io.github.parzivalExe.guiApi.events;

import io.github.parzivalExe.guiApi.Gui;
import io.github.parzivalExe.guiApi.components.SettingOption;
import io.github.parzivalExe.guiApi.components.Settings;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

@SuppressWarnings("unused")
public class SettingsClickedEvent extends ComponentClickedEvent {

    private final SettingOption clickedOption;
    private final SettingOption newSetting;

    public SettingsClickedEvent(Settings clickedSettings, SettingOption clickedOption, SettingOption newSetting, HumanEntity whoClicked, Gui clickedGui, InventoryAction action, int slot, ClickType clickType) {
        super(clickedSettings, whoClicked, clickedGui, action, slot, clickType);
        this.clickedOption = clickedOption;
        this.newSetting = newSetting;

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
