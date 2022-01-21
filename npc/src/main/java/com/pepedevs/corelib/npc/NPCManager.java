package com.pepedevs.corelib.npc;

import com.github.retrooper.packetevents.PacketEvents;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NPCManager {

    private final Map<String, NPC> npcs = new ConcurrentHashMap<>();

    private final Plugin plugin;
    private NPCPacketListener packetListener;

    public NPCManager(Plugin plugin) {
        this.plugin = plugin;
        this.packetListener = new NPCPacketListener(this);
        PacketEvents.getAPI().getEventManager().registerListener(packetListener);
    }

    public NPC getNPC(String id) {
        return npcs.get(id);
    }

    public PlayerNPC createPlayerNPC(String id, Location location) {
        PlayerNPC npc = new PlayerNPC(id, location);
        npcs.put(id, npc);
        return npc;
    }

    public EntityNPC createEntityNPC(String id, EntityType entityType, Location location) {
        EntityNPC npc = new EntityNPC(id, entityType, location);
        npcs.put(id, npc);
        return npc;
    }

    public NPC removeNPC(String id) {
        NPC npc = npcs.remove(id);
        if (npc != null) {
            npc.destroy();
        }
        return npc;
    }

    public Map<String, NPC> getNpcs() {
        return npcs;
    }

    public void destroy() {
        for (NPC npc : npcs.values()) {
            npc.destroy();
        }
        npcs.clear();
//        PacketEvents.getAPI().getEventManager().unregisterListener(NPCPacketListener);
    }

}
