package com.pepedevs.corelib.npc;

import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutEntityDestroy;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutEntityLook;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutEntityTeleport;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutSpawnEntityLiving;
import com.pepedevs.corelib.npc.internal.NpcBase;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class EntityNPC extends NpcBase {

    private final EntityType entityType;

    public EntityNPC(EntityType entityType, Location location) {
        super(location);
        this.entityType = entityType;
    }

    public EntityType getEntityType() {
        return this.entityType;
    }

    @Override
    protected void view(Player player) {
        WrappedPacketPlayOutSpawnEntityLiving packet = PACKET_PROVIDER.getNewSpawnEntityLivingPacket(this.getEntityId(), this.getUuid(), this.entityType, this.location, this.location.getYaw(), NMS_PROVIDER.getDataWatcher());
        NMS_PROVIDER.getPlayer(player).sendPacket(packet);
    }

    @Override
    protected void destroy(Player player) {
        WrappedPacketPlayOutEntityDestroy packet = PACKET_PROVIDER.getNewEntityDestroyPacket(this.getEntityId());
        NMS_PROVIDER.getPlayer(player).sendPacket(packet);
    }

    @Override
    protected void changeLocation(Player player) {
        WrappedPacketPlayOutEntityTeleport packet = PACKET_PROVIDER.getNewEntityTeleportPacket(this.getEntityId(), this.location);
        NMS_PROVIDER.getPlayer(player).sendPacket(packet);
    }

    @Override
    protected void changeFov(Player player, float yaw, float pitch) {
        WrappedPacketPlayOutEntityLook packet = PACKET_PROVIDER.getNewEntityLookPacket(this.getEntityId(), yaw, pitch, true);
        NMS_PROVIDER.getPlayer(player).sendPacket(packet);
    }

}
