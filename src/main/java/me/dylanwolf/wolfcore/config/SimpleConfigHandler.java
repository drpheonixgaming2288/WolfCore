package me.dylanwolf.wolfcore.config;

import com.google.common.collect.Maps;
import me.dylanwolf.wolfcore.WolfCore;
import org.bukkit.plugin.Plugin;

import java.util.Map;

public class SimpleConfigHandler implements IConfigHandler {
    private final Map<String, WolfConfig> configs;
    private final Plugin plugin;

    public SimpleConfigHandler(Plugin plugin) {
        configs = Maps.newHashMap();
        this.plugin = plugin;
    }

    @Override
    public void registerConfig(String name) {
        WolfConfig config = new WolfConfig(this.plugin, name);
        configs.put(config.getName(), config);
    }

    @Override
    public Map<String, WolfConfig> getConfigs() {
        return configs;
    }

    @Override
    public WolfConfig getConfig(String name) {

        return configs.getOrDefault(name, null);
    }
}
