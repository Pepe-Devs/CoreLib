package com.pepedevs.radium.nms.packets;

public interface WrappedPacketPlayOutRelEntityMoveLook extends WrappedPacket {

    int getEntityId();

    void setEntityId(int entityId);

    int getDeltaX();

    void setDeltaX(int xDiff);

    int getDeltaY();

    void setDeltaY(int yDiff);

    int getDeltaZ();

    void setDeltaZ(int zDiff);

    float getYaw();

    void setYaw(float yaw);

    float getPitch();

    void setPitch(float pitch);

    boolean isOnGround();

    void setOnGround(boolean onGround);

}
