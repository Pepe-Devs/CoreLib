package com.pepedevs.corelib.nms.v1_12_R1.packets;

import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutTabComplete;
import com.pepedevs.corelib.nms.v1_12_R1.NMSProviderImpl;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutTabComplete;

import java.io.IOException;

public class WrappedPacketPlayOutTabCompleteImpl implements WrappedPacketPlayOutTabComplete {

    private String[] completions;

    public WrappedPacketPlayOutTabCompleteImpl(String... completions) {
        this.completions = completions;
    }

    public WrappedPacketPlayOutTabCompleteImpl(WrappedPacketDataSerializer serializer) {
        PacketDataSerializer dataSerializer = (PacketDataSerializer) serializer;
        this.completions = new String[dataSerializer.g()];
        for (int i = 0; i < this.completions.length; i++) {
            this.completions[i] = dataSerializer.e(32767);
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
