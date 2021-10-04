package io.github.parzivalExe.guiApi.components;

import io.github.parzivalExe.guiApi.Gui;
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLContent;
import io.github.parzivalExe.guiApi.objects.Command;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings("unused")
public class ExecuteCommandComponent extends Component{

    @XMLContent(necessary = true)
    private ArrayList<Command> commands = new ArrayList<>();

    public ExecuteCommandComponent(ComponentMeta meta, ArrayList<Command> commands) {
        super(meta);
        this.commands = commands;
    }
    public ExecuteCommandComponent(ComponentMeta meta, Command command) {
        super(meta);
        if(command != null) {
            this.commands.add(command);
        }
    }
    public ExecuteCommandComponent(ComponentMeta meta) {
        this(meta, (Command) null);
    }
    public ExecuteCommandComponent() {
        this(new ComponentMeta("", new ItemStack(Material.WOOL)));
    }

    @Override
    public void componentClicked(@NotNull HumanEntity whoClicked, @NotNull Gui gui, @NotNull InventoryAction action, int slot, @NotNull ClickType clickType) {
        if(whoClicked instanceof Player) {

            //noinspection ConstantConditions
            if(commands != null || commands.size() > 0) {
                for(Command command : commands) {
                    ((Player) whoClicked).performCommand(command.buildCommand());
                }
            }else{
                whoClicked.sendMessage("This component has no Commands implemented right now");
            }

        }else{
            whoClicked.sendMessage("Normally, only players can click Components");
        }
        gui.closeGui();
    }

    public void addCommand(Command command) {
        this.commands.add(command);
    }
    public void addCommands(Collection<Command> commands) {
        this.commands.addAll(commands);
    }

    public void setCommands(ArrayList<Command> commands) {
        this.commands = commands;
    }

    public void clearCommands() {
        commands.clear();
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }
}