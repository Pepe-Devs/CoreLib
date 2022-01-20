package com.pepedevs.corelib.npc.internal;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.PacketEventsAPI;
import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.util.MathUtil;
import com.github.retrooper.packetevents.util.Vector3d;
import com.github.retrooper.packetevents.wrapper.play.server.*;
import com.pepedevs.corelib.npc.NPC;
import com.pepedevs.corelib.npc.action.NPCClickAction;
import com.pepedevs.corelib.utils.reflection.bukkit.EntityReflection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.*;

public abstract class NpcBase implements NPC {

    protected static final PacketEventsAPI<?> PACKET_EVENTS_API = PacketEvents.getAPI();

    private final NPCData npcData;

    protected final int entityId;
    protected final UUID uuid;
    protected Location location;

    protected Set<NPCClickAction> clickActions = Collections.synchronizedSet(new HashSet<>());
    protected Set<UUID> shown = Collections.synchronizedSet(new HashSet<>());

    public NpcBase(Location location) {
        this.npcData = new NPCData();
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
        if (this.location.getWorld() == null) throw new NullPointerException("World can't be null");
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
            if (player != null) this.changeFov(player, yaw, pitch);
            else this.shown.remove(uuid);
        }
    }

    @Override
    public void lookAt(Vector direction) {
        Location location = new Location(null, 0, 1, 0);
        location.setDirection(direction);
        this.look(location.getYaw(), location.getPitch());
    }

    @Override
    public void lookAt(Location location) {
        lookAt(this.location.toVector().subtract(location.toVector()).normalize());
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

    public void setCrouching(boolean a) {
        this.npcData.setCrouched(a);
    }

    public boolean isCrouching() {
        return this.npcData.isCrouched();
    }

    public void setOnFire(boolean a) {
        this.npcData.setOnFire(a);
    }

    public boolean isOnFire() {
        return this.npcData.isOnFire();
    }

    private void updateNPCData() {
        EntityData entityData = new EntityData(0, EntityDataTypes.BYTE, this.npcData.buildByte());
        WrapperPlayServerEntityMetadata packet = new WrapperPlayServerEntityMetadata(this.entityId, Collections.singletonList(entityData));
        for (UUID uuid : this.shown) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) continue;
            PACKET_EVENTS_API.getPlayerManager().sendPacket(player, packet);
        }
    }

    protected void view(Player player) {

    }

    protected Vector3d convert(Location location) {
        return new Vector3d(location.getX(), location.getY(), location.getZ());
    }

    protected void destroy(Player player) {
        WrapperPlayServerDestroyEntities packet = new WrapperPlayServerDestroyEntities(this.entityId);
        PACKET_EVENTS_API.getPlayerManager().sendPacket(player, packet);
    }

    protected void changeLocation(Player player) {
        WrapperPlayServerEntityTeleport packet = new WrapperPlayServerEntityTeleport(this.getEntityId(),
                convert(this.location),
                this.location.getYaw(),
                this.location.getPitch(),
                true);
        PACKET_EVENTS_API.getPlayerManager().sendPacket(player, packet);
    }

    protected void changeFov(Player player, float yaw, float pitch) {
        WrapperPlayServerEntityRotation packet = new WrapperPlayServerEntityRotation(this.getEntityId(),
                (byte) MathUtil.floor(yaw * 256.0F / 360.0F),
                (byte) MathUtil.floor(pitch * 256.0F / 360.0F),
                true);
        PACKET_EVENTS_API.getPlayerManager().sendPacket(player, packet);
    }

}
