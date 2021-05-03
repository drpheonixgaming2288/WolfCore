package me.dylanwolf.wolfcore.command;

import com.google.common.collect.Maps;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SimpleCommandHandler implements ICommandHandler {
    private final HashMap<Class<? extends WolfCommand>, WolfCommand> commands;
    private final JavaPlugin javaPlugin;


    /**
     * Creates a new instance of an automated CommandHandler
     *
     * @param javaPlugin This is the plugin instance to which the command will be registered
     * @param _package   the package in which all of your commands reside
     **/
    public SimpleCommandHandler(JavaPlugin javaPlugin, String _package) {
        /*
        Yes, I know this looks like some mad magic, but it bascially just registers all classes that extend WolfCommand as commands.
         */
        this.javaPlugin = javaPlugin;
        commands = Maps.newHashMap();
        try (ScanResult scanResult = new ClassGraph().acceptPackages(_package)
                .enableClassInfo().scan()) {
            List<Class<WolfCommand>> result = scanResult
                    .getSubclasses(WolfCommand.class.getName())
                    .loadClasses(WolfCommand.class);

            result.forEach(s -> {
                try {
                    WolfCommand cmd = s.getConstructor(JavaPlugin.class).newInstance(this.javaPlugin);

                    commands.put(s, cmd);
                } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    javaPlugin.getLogger().severe("Command " + s.getName() + " could not be instantiated. Possible reason: Missing or wrong constructor");
                    e.printStackTrace();
                }

            });
        }


    }

    @Override
    public <T extends WolfCommand> T getCommand(Class<T> commandClass) {
        T r;
        if (commandClass == null) return null;
            //Ignore Warnings if this breaks, there is more important stuff to worry about
        else r = (T) commands.getOrDefault(commandClass, null);
        if (r == null)
            throw new RuntimeException("This Command was not found. (This should in theory be impossible, what did you do??) " + commandClass.getName());
        return r;
    }

    @Override
    public List<WolfCommand> getCommands() {
        return new ArrayList<>(commands.values());
    }

}
