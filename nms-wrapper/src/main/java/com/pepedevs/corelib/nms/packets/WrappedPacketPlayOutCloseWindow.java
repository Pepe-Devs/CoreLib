package com.pepedevs.corelib.nms.packets;

public interface WrappedPacketPlayOutCloseWindow extends WrappedPacket {
    int getContainerID();

    void setContainerID(int containerID);
}
