package com.pepedevs.corelib.nms.v1_8_R3.packets;

import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutTabComplete;
import com.pepedevs.corelib.nms.v1_8_R3.NMSProviderImpl;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutTabComplete;

import java.io.IOException;

public class WrappedPacketPlayOutTabCompleteImpl extends PacketPlayOutTabComplete implements WrappedPacketPlayOutTabComplete {

    private String[] completions;

    public WrappedPacketPlayOutTabCompleteImpl(String... completions) {
        this.completions = completions;
    }

    public WrappedPacketPlayOutTabCompleteImpl(WrappedPacketDataSerializer serializer) {
        PacketDataSerializer dataSerializer = (PacketDataSerializer) serializer;
        this.completions = new String[dataSerializer.e()];
        for (int i = 0; i < this.completions.length; i++) {
            this.completions[i] = dataSerializer.c(32767);
        }
    }

    @Override
    public String[] getCompletions() {
        return completions;
    }

    @Override
    public void setCompletions(String... completions) {
        this.completions = completions;
    }

    @Override
    public WrappedPacketDataSerializer buildData() {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        serializer.serializeIntToByte(this.completions.length);
        for (String completion : this.completions) {
            serializer.serializeString(completion);
        }
        return serializer;
    }

    @Override
    public Object buildPacket() {
        PacketPlayOutTabComplete packet = new PacketPlayOutTabComplete();
        try {
            packet.a((PacketDataSerializer) buildData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packet;
    }
}
