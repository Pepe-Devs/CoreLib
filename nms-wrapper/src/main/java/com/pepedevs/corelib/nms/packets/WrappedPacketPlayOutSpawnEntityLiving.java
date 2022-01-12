package com.pepedevs.corelib.nms.packets;

import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import java.util.UUID;

public interface WrappedPacketPlayOutSpawnEntityLiving extends WrappedPacket {

    int getEntityId();

    void setEntityId(int entityId);

    UUID getUuid();

    void setUuid(UUID uuid);

    EntityType getEntityType();

    void setEntityType(EntityType entityType);

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

    float getHeadRotation();

    void setHeadRotation(float headRotation);

    Vector getVelocity();

    void setVelocity(Vector velocity);

    Object getDataWatcher();

    void setDataWatcher(Object dataWatcher);

}
