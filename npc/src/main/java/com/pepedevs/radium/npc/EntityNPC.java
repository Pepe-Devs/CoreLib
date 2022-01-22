package com.pepedevs.radium.npc;

import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import com.github.retrooper.packetevents.util.Vector3d;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnLivingEntity;
import com.pepedevs.radium.npc.internal.NpcBase;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class EntityNPC extends NpcBase {

    private final EntityType entityType;

    public EntityNPC(String id, EntityType entityType, Location location) {
        super(id, location);
        this.entityType = entityType;
    }

    public EntityType getEntityType() {
        return this.entityType;
    }

    @Override
    protected void view(Player player) {
        WrapperPlayServerSpawnLivingEntity packet = new WrapperPlayServerSpawnLivingEntity(this.getEntityId(),
                this.getUuid(),
                EntityTypes.getByName(this.entityType.name().toLowerCase()),
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
