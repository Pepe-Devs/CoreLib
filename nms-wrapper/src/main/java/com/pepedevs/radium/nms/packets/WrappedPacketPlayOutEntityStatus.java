package com.pepedevs.radium.nms.packets;

import com.pepedevs.radium.nms.EntityStatus;

public interface WrappedPacketPlayOutEntityStatus extends WrappedPacket{
    int getEntityID();

    byte getStatus();

    void setEntityID(int entityID);

    void setStatus(byte status);

    void setStatus(EntityStatus status);
}
