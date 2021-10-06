package io.github.parzivalExe.guiApi.objects;

import io.github.parzivalExe.guiApi.antlr.interfaces.XMLAttribute;

import java.util.ArrayList;

public class Command {

    @XMLAttribute(necessary = true)
    private String command;
    @XMLAttribute
    private ArrayList<String> arguments;

    public Command(String command, ArrayList<String> arguments) {
        this.command = command;
        this.arguments = arguments;
    }
    public Command(String command) {
        this(command, null);
    }
    public Command() {
        this(null);
    }

    public String buildCommand() {
        StringBuilder command = new StringBuilder(getCommand());

        for(String argument : arguments) {
            command.append(" ").append(argument);
        }
        return command.toString();
    }

    //region Setter

    public void setCommand(String command) {
        this.command = command;
    }

    public void setArguments(ArrayList<String> arguments) {
        this.arguments = arguments;
    }

    public void addArgument(String argument) {
        arguments.add(argument);
    }

    public void addArgument(String argument, int index) {
        arguments.add(index, argument);
    }

    //endregion

    //region Getter

    public String getCommand() {
        return command;
    }

    public ArrayList<String> getArguments() {
        return arguments;
    }

    public String getArgumentAtIndex(int index) {
        return arguments.get(index);
    }

    //endregion

}
