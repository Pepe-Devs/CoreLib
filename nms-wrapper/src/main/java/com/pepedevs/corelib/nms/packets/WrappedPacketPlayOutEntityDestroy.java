package com.pepedevs.corelib.nms.packets;

public interface WrappedPacketPlayOutEntityDestroy extends WrappedPacket {
    int[] getEntityIDs();

    void setEntityIDs(int[] entityIDs);
}
