package com.pepedevs.corelib.nms.v1_12_R1.packets;

import com.pepedevs.corelib.nms.packets.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutCloseWindow;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutCloseWindow;

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
