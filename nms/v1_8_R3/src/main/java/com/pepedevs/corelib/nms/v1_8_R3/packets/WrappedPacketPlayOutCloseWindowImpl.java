package com.pepedevs.corelib.nms.v1_8_R3.packets;

import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutCloseWindow;
import net.minecraft.server.v1_8_R3.PacketPlayOutCloseWindow;

public class WrappedPacketPlayOutCloseWindowImpl extends PacketPlayOutCloseWindow implements WrappedPacketPlayOutCloseWindow {

    public WrappedPacketPlayOutCloseWindowImpl(int containerId) {
        super(containerId);
    }

}
