package com.pepedevs.corelib.nms.v1_8_R3.packets;

import com.pepedevs.corelib.nms.packets.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutScoreboardScore;
import com.pepedevs.corelib.nms.v1_8_R3.NMSImpl;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardScore;

import java.io.IOException;

public class WrappedPacketPlayOutScoreboardScoreImpl extends PacketPlayOutScoreboardScore implements WrappedPacketPlayOutScoreboardScore {

    public WrappedPacketPlayOutScoreboardScoreImpl(String playerName, String objectiveName, int value, ScoreboardAction action) {
        WrappedPacketDataSerializer serializer = NMSImpl.INSTANCE.getDataSerializer();
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

}
