package com.pepedevs.corelib.nms.v1_8_R3.packets;

import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutTabComplete;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutTabComplete;

import java.io.IOException;

public class WrappedPacketPlayOutTabCompleteImpl extends PacketPlayOutTabComplete implements WrappedPacketPlayOutTabComplete {

    public WrappedPacketPlayOutTabCompleteImpl(String... completions) {
        super(completions);
    }

    public WrappedPacketPlayOutTabCompleteImpl(WrappedPacketDataSerializer serializer) {
        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
