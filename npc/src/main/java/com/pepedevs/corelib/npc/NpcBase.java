package com.pepedevs.corelib.npc;

import com.pepedevs.corelib.nms.NMSBridge;
import com.pepedevs.corelib.nms.NMSProvider;
import com.pepedevs.corelib.nms.PacketProvider;
import com.pepedevs.corelib.utils.reflection.bukkit.EntityReflection;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public abstract class NpcBase implements NPC {

    protected static final PacketProvider PACKET_PROVIDER = NMSBridge.getPacketProvider();
    protected static final NMSProvider NMS_PROVIDER = NMSBridge.getNMSProvider();

    private final int entityId;
    private final UUID uuid;
    protected Location location;

    protected Set<UUID> shown = Collections.synchronizedSet(new HashSet<>());

    public NpcBase(Location location) {
        this.entityId = EntityReflection.getFreeEntityId();
        this.location = location;
        this.uuid = UUID.randomUUID();
    }

    protected int getEntityId() {
        return entityId;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public Set<UUID> getViewers() {
        return Collections.unmodifiableSet(this.shown);
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    protected abstract void view(Player player);

    protected abstract void destroy(Player player);

    protected abstract void changeLocation(Player player);

    protected abstract void changeFov(Player player, Vector direction);

}
