package com.pepedevs.radium.nms.packets;

public interface WrappedPacketPlayOutTabComplete extends WrappedPacket {
    String[] getCompletions();

    void setCompletions(String... completions);
}
