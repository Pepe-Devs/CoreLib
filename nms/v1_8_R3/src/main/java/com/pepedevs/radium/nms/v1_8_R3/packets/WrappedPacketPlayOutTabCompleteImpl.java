package com.pepedevs.radium.nms.v1_8_R3.packets;

import com.pepedevs.radium.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.radium.nms.packets.WrappedPacketPlayOutTabComplete;
import com.pepedevs.radium.nms.v1_8_R3.NMSProviderImpl;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutTabComplete;

import java.io.IOException;

public class WrappedPacketPlayOutTabCompleteImpl implements WrappedPacketPlayOutTabComplete {

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
        serializer.serializeVarInt(this.completions.length);
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
