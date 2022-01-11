package com.pepedevs.corelib.nms.v1_12_R1.packets;

import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutScoreboardScore;
import com.pepedevs.corelib.nms.v1_12_R1.NMSProviderImpl;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutScoreboardScore;

import java.io.IOException;

public class WrappedPacketPlayOutScoreboardScoreImpl extends PacketPlayOutScoreboardScore implements WrappedPacketPlayOutScoreboardScore {

    public WrappedPacketPlayOutScoreboardScoreImpl(String playerName, String objectiveName, int value, ScoreboardAction action) {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        serializer.serializeString(playerName)
                .serializeEnum(action)
                .serializeString(objectiveName);
        if (action != ScoreboardAction.REMOVE) {
            serializer.serializeIntToByte(value);
        }

        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WrappedPacketPlayOutScoreboardScoreImpl(WrappedPacketDataSerializer serializer) {
        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
