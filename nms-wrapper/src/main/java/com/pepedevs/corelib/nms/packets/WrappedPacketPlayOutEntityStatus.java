package com.pepedevs.corelib.nms.packets;

import com.pepedevs.corelib.nms.EntityStatus;

public interface WrappedPacketPlayOutEntityStatus extends WrappedPacket{
    int getEntityID();

    byte getStatus();

    void setEntityID(int entityID);

    void setStatus(byte status);

    void setStatus(EntityStatus status);
}
