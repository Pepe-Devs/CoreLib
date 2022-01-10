package com.pepedevs.corelib.nms.v1_8_R3.packets;

import com.pepedevs.corelib.nms.packets.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutTabComplete;
import com.pepedevs.corelib.nms.v1_8_R3.NMSImpl;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutTabComplete;

import java.io.IOException;

public class WrappedPacketPlayOutTabCompleteImpl extends PacketPlayOutTabComplete implements WrappedPacketPlayOutTabComplete {

    public WrappedPacketPlayOutTabCompleteImpl(String... completions) throws IOException {
        WrappedPacketDataSerializer serializer = NMSImpl.INSTANCE.getDataSerializer();
        serializer.serializeIntToByte(completions.length);
        for (String completion : completions) {
            serializer.serializeString(completion);
        }
        this.a((PacketDataSerializer) serializer);
    }

}
