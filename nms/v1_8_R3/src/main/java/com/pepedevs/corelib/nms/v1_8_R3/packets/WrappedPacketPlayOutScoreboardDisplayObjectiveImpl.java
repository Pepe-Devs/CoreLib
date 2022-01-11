package com.pepedevs.corelib.nms.v1_8_R3.packets;

import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutScoreboardDisplayObjective;
import com.pepedevs.corelib.nms.v1_8_R3.NMSProviderImpl;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardDisplayObjective;

import java.io.IOException;

public class WrappedPacketPlayOutScoreboardDisplayObjectiveImpl extends PacketPlayOutScoreboardDisplayObjective implements WrappedPacketPlayOutScoreboardDisplayObjective {

    public WrappedPacketPlayOutScoreboardDisplayObjectiveImpl(ScoreboardPosition position, String name) {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        serializer.serializeByte(position.ordinal());
        if (name == null) {
            serializer.serializeString("");
        } else {
            serializer.serializeString(name);
        }

        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WrappedPacketPlayOutScoreboardDisplayObjectiveImpl(int position, String name) {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        serializer.serializeByte(position);
        if (name == null) {
            serializer.serializeString("");
        } else {
            serializer.serializeString(name);
        }

        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WrappedPacketPlayOutScoreboardDisplayObjectiveImpl(WrappedPacketDataSerializer serializer) {
        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
