package com.pepedevs.radium.nms.packets;

public interface WrappedPacketPlayOutEntityTeleport extends WrappedPacket {

    int getEntityId();

    void setEntityId(int entityId);

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

    boolean isOnGround();

    void setOnGround(boolean onGround);

}
