package com.pepedevs.radium.nms.packets;

public interface WrappedPacketPlayOutCloseWindow extends WrappedPacket {
    int getContainerID();

    void setContainerID(int containerID);
}
