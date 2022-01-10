package com.pepedevs.corelib.nms.v1_8_R3.packets;

import com.pepedevs.corelib.nms.packets.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutCloseWindow;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutCloseWindow;

import java.io.IOException;

public class WrappedPacketPlayOutCloseWindowImpl extends PacketPlayOutCloseWindow implements WrappedPacketPlayOutCloseWindow {

    public WrappedPacketPlayOutCloseWindowImpl(int containerID) {
        super(containerID);
    }

    public WrappedPacketPlayOutCloseWindowImpl(WrappedPacketDataSerializer serializer) {
        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
