package com.pepedevs.corelib.npc.internal;

import com.pepedevs.corelib.nms.NMSBridge;
import com.pepedevs.corelib.nms.NMSProvider;
import com.pepedevs.corelib.nms.PacketProvider;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutEntityDestroy;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutEntityLook;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutEntityTeleport;
import com.pepedevs.corelib.npc.NPC;
import com.pepedevs.corelib.npc.action.NPCClickAction;
import com.pepedevs.corelib.utils.reflection.bukkit.EntityReflection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.*;

public abstract class NpcBase implements NPC {

    protected static final PacketProvider PACKET_PROVIDER = NMSBridge.getPacketProvider();
    protected static final NMSProvider NMS_PROVIDER = NMSBridge.getNMSProvider();

    protected final int entityId;
    protected final UUID uuid;
    protected Location location;

    protected Set<NPCClickAction> clickActions = Collections.synchronizedSet(new HashSet<>());
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
    public void spawn() {
        for (Player player : this.location.getWorld().getPlayers()) {
            this.view(player);
            this.shown.add(player.getUniqueId());
        }
    }

    @Override
    public void teleport(Location location) {
        if (this.location.equals(location))
            return;

        this.location = location;
        for (UUID uuid : this.shown) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                this.changeLocation(player);
            } else {
                this.shown.remove(uuid);
            }
        }
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    @Override
    public void look(float yaw, float pitch) {
        for (UUID uuid : this.shown) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                this.changeFov(player, yaw, pitch);
            } else {
                this.shown.remove(uuid);
            }
        }
    }

    @Override
    public void lookAt(Vector direction) {

    }

    @Override
    public void lookAt(Location location) {

    }

    @Override
    public void destroy() {
        for (UUID uuid : this.shown) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                this.destroy(player);
            }
        }
        this.shown.clear();
    }

    @Override
    public Set<UUID> getViewers() {
        return Collections.unmodifiableSet(this.shown);
    }

    @Override
    public void hide(Player player) {
        if (!this.shown.contains(player.getUniqueId()))
            return;

        this.destroy(player);
        this.shown.remove(player.getUniqueId());
    }

    @Override
    public void show(Player player) {
        if (this.shown.contains(player.getUniqueId()))
            return;

        this.view(player);
        this.shown.add(player.getUniqueId());
    }

    @Override
    public Collection<NPCClickAction> getClickActions() {
        return this.clickActions;
    }

    @Override
    public void addClickAction(NPCClickAction action) {
        this.clickActions.add(action);
    }

    protected void view(Player player) {
    }

    protected void destroy(Player player) {
        WrappedPacketPlayOutEntityDestroy packet = PACKET_PROVIDER.getNewEntityDestroyPacket(this.entityId);
        NMS_PROVIDER.getPlayer(player).sendPacket(packet);
    }

    protected void changeLocation(Player player) {
        WrappedPacketPlayOutEntityTeleport packet = PACKET_PROVIDER.getNewEntityTeleportPacket(this.getEntityId(), this.location);
        NMS_PROVIDER.getPlayer(player).sendPacket(packet);
    }

    protected void changeFov(Player player, float yaw, float pitch) {
        WrappedPacketPlayOutEntityLook packet = PACKET_PROVIDER.getNewEntityLookPacket(this.getEntityId(), yaw, pitch, true);
        NMS_PROVIDER.getPlayer(player).sendPacket(packet);
    }

}
