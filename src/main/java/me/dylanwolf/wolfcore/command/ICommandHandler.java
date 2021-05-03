package me.dylanwolf.wolfcore.command;

import java.util.List;

public interface ICommandHandler {


    /**
     * gets a command by the Class. Every command should be registered by the underlying implementation of <code>ICommandHandler</code>
     * <br>
     * <code>SimpleCommandHandler</code> is the base implementation and should be applicable to most use-cases.
     *
     * @param commandClass Class of the Command you want to retrieve.
     * @return the Command Instance, might be null depending  the underlying implementation
     **/
    <T extends WolfCommand> T getCommand(Class<T> commandClass);

    /**
     * Returns a List of all Commands registered by this instance. There should only be one instance per plugin except for special use-cases, so this should return all commands.
     *
     * @return List of all Commands
     */
    List<WolfCommand> getCommands();
}
