package com.pepedevs.radium.nms.packets;

public interface WrappedPacketPlayOutEntityLook extends WrappedPacket {

    int getEntityId();

    void setEntityId(int entityId);

    float getYaw();

    void setYaw(float yaw);

    float getPitch();

    void setPitch(float pitch);

    boolean isOnGround();

    void setOnGround(boolean onGround);

}
