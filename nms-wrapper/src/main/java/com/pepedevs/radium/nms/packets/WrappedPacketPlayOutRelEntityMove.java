package com.pepedevs.radium.nms.packets;

public interface WrappedPacketPlayOutRelEntityMove extends WrappedPacket {

    int getEntityId();

    void setEntityId(int entityId);

    int getDeltaX();

    void setDeltaX(int deltaX);

    int getDeltaY();

    void setDeltaY(int deltaY);

    int getDeltaZ();

    void setDeltaZ(int deltaZ);

    boolean isOnGround();

    void setOnGround(boolean onGround);

}
