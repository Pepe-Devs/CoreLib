package com.pepedevs.corelib.npc;

import com.github.retrooper.packetevents.protocol.entity.type.EntityType;
import com.github.retrooper.packetevents.util.Vector3d;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnLivingEntity;
import com.pepedevs.corelib.npc.internal.NpcBase;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

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
        WrapperPlayServerSpawnLivingEntity packet = new WrapperPlayServerSpawnLivingEntity(this.getEntityId(),
                this.getUuid(),
                this.entityType,
                super.convert(this.location),
                this.location.getYaw(),
                this.location.getPitch(),
                this.location.getYaw(),
                Vector3d.zero(),
                new ArrayList<>()
                );
        PACKET_EVENTS_API.getPlayerManager().sendPacket(player, packet);
    }

}
