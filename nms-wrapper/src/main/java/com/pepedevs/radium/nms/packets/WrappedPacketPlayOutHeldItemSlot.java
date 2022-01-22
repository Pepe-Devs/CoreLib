package com.pepedevs.radium.nms.packets;

public interface WrappedPacketPlayOutHeldItemSlot extends WrappedPacket {
    int getSlot();

    void setSlot(int slot);
}
