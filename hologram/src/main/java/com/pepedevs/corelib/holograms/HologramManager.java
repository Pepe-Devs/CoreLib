package com.pepedevs.corelib.holograms;

import com.pepedevs.corelib.holograms.listener.HologramListener;
import com.pepedevs.corelib.holograms.listener.HologramPacketListener;
import com.pepedevs.corelib.holograms.object.DefaultHologram;
import com.pepedevs.corelib.holograms.object.Hologram;
import com.pepedevs.corelib.packets.PacketChannelHandler;
import com.pepedevs.corelib.packets.PacketListener;
import com.pepedevs.corelib.task.Task;
import com.pepedevs.corelib.task.TaskQueueHandler;
import org.bukkit.Location;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HologramManager {

    private static HologramManager instance;

    private final Plugin handle;
    private final Task thread;
    private final Map<String, Hologram> holograms;
    private final HologramPacketListener packetListener;
    private final HologramListener hologramListener;

    public HologramManager(Plugin plugin, Task thread) {
        instance = this;
        this.handle = plugin;
        this.thread = thread;
        this.holograms = new ConcurrentHashMap<>();
        this.packetListener = new HologramPacketListener();
        PacketChannelHandler.getInstance(this.handle)
                .addPacketListener("PacketPlayInUseEntity", PacketListener.Priority.NORMAL, this.packetListener);
        this.hologramListener = new HologramListener(this);
        this.handle.getServer().getPluginManager().registerEvents(this.hologramListener, this.handle);
    }

    public HologramManager(Plugin plugin) {
        this(plugin, new TaskQueueHandler("Hologram Thread %d")
                .newPool(1, 3 * 1000000));
    }

    public Map<String, Hologram> getHolograms() {
        return Collections.unmodifiableMap(this.holograms);
    }

    public Hologram createHologram(String id, Location location) {
        DefaultHologram hologram = new DefaultHologram(id, location);
        this.holograms.put(id, hologram);
        return hologram;
    }

    public Hologram getHologram(String id) {
        return this.holograms.get(id);
    }

    public boolean hasHologram(String id) {
        return this.holograms.containsKey(id);
    }

    public void deleteHologram(String id) {
        Hologram hologram = this.holograms.remove(id);
        hologram.destroy();
    }

    public void destroy() {
        for (Hologram value : this.holograms.values()) {
            value.destroy();
        }
        this.holograms.clear();
        PacketChannelHandler.getInstance(this.handle).removePacketListener(this.packetListener);
        this.thread.cancel();
        HandlerList.unregisterAll(this.hologramListener);
        instance = null;
    }

    public Task getThread() {
        return this.thread;
    }

    public static HologramManager get() {
        return instance;
    }

}
