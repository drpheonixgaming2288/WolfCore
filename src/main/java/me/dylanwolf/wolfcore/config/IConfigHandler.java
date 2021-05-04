package me.dylanwolf.wolfcore.config;

import java.util.Map;

public interface IConfigHandler {
    void registerConfig(String configName);

    Map<String, WolfConfig> getConfigs();

    WolfConfig getConfig(String configName);
}
