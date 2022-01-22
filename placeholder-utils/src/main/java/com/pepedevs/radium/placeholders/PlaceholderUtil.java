package com.pepedevs.radium.placeholders;

import com.pepedevs.radium.events.PluginLoadEvent;
import com.pepedevs.radium.placeholders.events.PlaceholderManagerHookEvent;
import com.pepedevs.radium.placeholders.managers.CustomPlaceholderManager;
import com.pepedevs.radium.placeholders.managers.PAPIPlaceholderManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public final class PlaceholderUtil {

    private static PlaceholderManager manager = null;

    private PlaceholderUtil() {}

    public static PlaceholderValue<Byte> parseByte(Object obj) {
        if (obj instanceof Number) {
            return PlaceholderValue.fake(((Number) obj).byteValue());
        } else if (obj instanceof String) return PlaceholderValue.byteValue((String) obj);
        else return null;
    }

    public static PlaceholderValue<Short> parseShort(Object obj) {
        if (obj instanceof Number) {
            return PlaceholderValue.fake(((Number) obj).shortValue());
        } else if (obj instanceof String) return PlaceholderValue.shortValue((String) obj);
        else return null;
    }

    public static PlaceholderValue<Integer> parseInt(Object obj) {
        if (obj instanceof Number) {
            return PlaceholderValue.fake(((Number) obj).intValue());
        } else if (obj instanceof String) return PlaceholderValue.intValue((String) obj);
        else return null;
    }

    public static PlaceholderValue<Long> parseLong(Object obj) {
        if (obj instanceof Number) {
            return PlaceholderValue.fake(((Number) obj).longValue());
        } else if (obj instanceof String) return PlaceholderValue.longValue((String) obj);
        else return null;
    }

    public static PlaceholderValue<Float> parseFloat(Object obj) {
        if (obj instanceof Number) {
            return PlaceholderValue.fake(((Number) obj).floatValue());
        } else if (obj instanceof String) return PlaceholderValue.floatValue((String) obj);
        else return null;
    }

    public static PlaceholderValue<Double> parseDouble(Object obj) {
        if (obj instanceof Number) {
            return PlaceholderValue.fake(((Number) obj).doubleValue());
        } else if (obj instanceof String) return PlaceholderValue.doubleValue((String) obj);
        else return null;
    }

    public static void tryHook(Plugin plugin) {
        PlaceholderManagerHookEvent event = new PlaceholderManagerHookEvent();
        PluginLoadEvent.onPluginLoaded(
                plugin,
                "PlaceholderAPI",
                pl -> {
                    Bukkit.getScheduler()
                            .runTask(plugin, () -> Bukkit.getPluginManager().callEvent(event));
                    manager = new PAPIPlaceholderManager();
                },
                () -> {
                    Bukkit.getScheduler()
                            .runTask(plugin, () -> Bukkit.getPluginManager().callEvent(event));
                    manager =
                            event.getPlaceholderManager() != null
                                    ? event.getPlaceholderManager()
                                    : new CustomPlaceholderManager(plugin);
                });
    }

    public static void register(Plugin plugin, Placeholder placeholder) {
        manager.register(plugin, placeholder);
    }

    public static PlaceholderValue<String> process(String message) {
        message = ChatColor.translateAlternateColorCodes('&', message);
        return PlaceholderValue.stringValue(message);
    }

    public static String resolve(Player player, String str) {
        return manager.apply(player, str);
    }

    public static String resolve(Player player, String str, PlaceholderRegistry<?> local) {
        return manager.apply(player, str, local);
    }

    public static String placeholder(Player player, String str) {
        return manager.apply(player, str);
    }

    public static boolean hasPlaceholders(String str) {
        return manager.hasPlaceholders(str);
    }

    public static PlaceholderRegistry<?> getRegistry() {
        return manager.getRegistry();
    }

    public static PlaceholderManager getManager() {
        return manager;
    }

    public static void setManager(PlaceholderManager manager) {
        PlaceholderUtil.manager = manager;
    }
}
