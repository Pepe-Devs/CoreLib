package com.pepedevs.corelib.nms.packets;

import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public interface WrappedPacketPlayOutNamedEntitySpawn extends WrappedPacket {

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

    ItemStack getMainHandItem();

    void setMainHandItem(ItemStack mainHandItem);

    Object getDataWatcher();

    void setDataWatcher(Object dataWatcher);

}
