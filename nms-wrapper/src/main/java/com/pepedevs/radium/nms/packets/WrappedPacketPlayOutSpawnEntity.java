package com.pepedevs.radium.nms.packets;

import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import java.util.UUID;

public interface WrappedPacketPlayOutSpawnEntity extends WrappedPacket {

    int getEntityId();

    void setEntityId(int entityId);

    UUID getUuid();

    void setUuid(UUID uuid);

    double getLocX();

    void setLocX(double locX);

    double getLocY();

    void setLocY(double locY);

    double getLocZ();

    void setLocZ(double locZ);

    float getYaw();

    void setYaw(float yaw);

    float getPitch();

    void setPitch(float pitch);

    Vector getVelocity();

    void setVelocity(Vector velocity);

    EntityType getEntityType();

    void setEntityType(EntityType entityType);

    int getData();

    void setData(int data);

}
