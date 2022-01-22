package com.pepedevs.radium.events;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

public interface EventHandler extends Listener, EventExecutor {

    static <T extends Event> EventHandler listen(
            Plugin plugin, Class<T> type, Consumer<T> listener) {
        return listen(plugin, type, EventPriority.NORMAL, listener);
    }

    static <T extends Event> EventHandler listen(
            Plugin plugin, Class<T> type, EventPriority priority, Consumer<T> listener) {
        return listen(plugin, type, priority, listener, false);
    }

    static <T extends Event> EventHandler listen(
            Plugin plugin,
            Class<T> type,
            EventPriority priority,
            Consumer<T> listener,
            boolean ignoredCancelled) {
        final EventHandler events = ($, event) -> listener.accept(type.cast(event));
        Bukkit.getPluginManager()
                .registerEvent(type, events, priority, events, plugin, ignoredCancelled);
        return events;
    }

    default void unregister() {
        HandlerList.unregisterAll(this);
    }
}
