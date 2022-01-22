package com.pepedevs.radium.nms.packets;

public interface WrappedPacketPlayOutEntityMetadata extends WrappedPacket {

    int getEntityId();

    void setEntityId(int entityId);

    Object getWatchableObjects();

    void setWatchableObjects(Object watchableObjects);

}
