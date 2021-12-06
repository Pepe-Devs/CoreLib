package com.pepedevs.corelib.events;

import com.pepedevs.corelib.utils.Pair;
import com.pepedevs.corelib.utils.server.ServerLifePhase;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/** Reference to a utility class for plugin. */
public class PluginLoadEvent {

    private PluginLoadEvent() {}

    /**
     * Triggers the consumer function on the given plugin loads.
     *
     * <p>
     *
     * @param pluginName Plugin to check for loading
     * @param callback Consumer to trigger when the plugin loads
     */
    public static void onPluginLoaded(
            Plugin handlingPlugin,
            String pluginName,
            Consumer<Plugin> callback,
            Runnable notFoundCallback) {
        if (Bukkit.getPluginManager().isPluginEnabled(pluginName)) {
            callback.accept(Bukkit.getPluginManager().getPlugin(pluginName));
        } else {
            Bukkit.getPluginManager()
                    .registerEvents(
                            new PluginLoadedListener(
                                    handlingPlugin, pluginName, callback, notFoundCallback),
                            handlingPlugin);
        }
    }

    private static class PluginLoadedListener implements Listener {

        private static final Map<Plugin, Pair<BukkitTask, List<PluginLoadedListener>>> INSTANCES =
                new ConcurrentHashMap<>();

        private final Plugin handlingPlugin;
        private final String pluginName;
        private final Consumer<Plugin> callback;
        private final Runnable notFoundCallback;

        private PluginLoadedListener(
                Plugin handlingPlugin,
                String pluginName,
                Consumer<Plugin> callback,
                Runnable notFoundCallback) {
            this.handlingPlugin = handlingPlugin;
            this.pluginName = pluginName;
            this.callback = callback;
            this.notFoundCallback = notFoundCallback;
            Pair<BukkitTask, List<PluginLoadedListener>> instance =
                    INSTANCES.getOrDefault(
                            this.handlingPlugin, new Pair<>(null, new ArrayList<>()));
            if (!INSTANCES.containsKey(this.handlingPlugin))
                INSTANCES.put(this.handlingPlugin, instance);
            instance.getValue().add(this);
            if (instance.getKey() == null) {
                instance.setKey(
                        Bukkit.getScheduler()
                                .runTaskTimerAsynchronously(
                                        this.handlingPlugin,
                                        () -> {
                                            if (ServerLifePhase.getLifePhase()
                                                    == ServerLifePhase.RUNNING) {
                                                Exception ex = null;
                                                for (PluginLoadedListener listener :
                                                        new ArrayList<>(instance.getValue())) {
                                                    HandlerList.unregisterAll(listener);
                                                    try {
                                                        listener.notFoundCallback.run();
                                                    } catch (Exception e) {
                                                        if (ex == null) ex = e;
                                                        else ex.addSuppressed(e);
                                                    }
                                                }
                                                INSTANCES.remove(this.handlingPlugin);
                                                if (ex != null) ex.printStackTrace();
                                                if (!instance.getKey().isCancelled())
                                                    instance.getKey().cancel();
                                            }
                                        },
                                        20L,
                                        20L));
            }
        }

        @EventHandler
        public void handlePluginEnable(PluginEnableEvent e) {
            if (!e.getPlugin().getName().equals(pluginName)) return;

            HandlerList.unregisterAll(this);

            callback.accept(e.getPlugin());
            INSTANCES.get(this.handlingPlugin).getValue().remove(this);
        }
    }
}
