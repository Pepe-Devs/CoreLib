package com.pepedevs.corelib.nms.v1_8_R3.packets;

import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutScoreboardScore;
import com.pepedevs.corelib.nms.v1_8_R3.NMSProviderImpl;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardScore;

import java.io.IOException;

public class WrappedPacketPlayOutScoreboardScoreImpl implements WrappedPacketPlayOutScoreboardScore {

    private String playerName;
    private String objectiveName;
    private int value;
    private ScoreboardAction action;

    public WrappedPacketPlayOutScoreboardScoreImpl(String playerName, String objectiveName, int value, ScoreboardAction action) {
        this.playerName = playerName;
        this.objectiveName = objectiveName;
        this.value = value;
        this.action = action;
    }

    public WrappedPacketPlayOutScoreboardScoreImpl(WrappedPacketDataSerializer serializer) {
        PacketDataSerializer dataSerializer = (PacketDataSerializer) serializer;
        this.playerName = dataSerializer.c(40);
        this.action = dataSerializer.a(ScoreboardAction.class);
        this.objectiveName = dataSerializer.c(16);
        if (this.action != ScoreboardAction.REMOVE) {
            this.value = dataSerializer.e();
        }
    }

    @Override
    public String getPlayerName() {
        return playerName;
    }

    @Override
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public String getObjectiveName() {
        return objectiveName;
    }

    @Override
    public void setObjectiveName(String objectiveName) {
        this.objectiveName = objectiveName;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public ScoreboardAction getAction() {
        return action;
    }

    @Override
    public void setAction(ScoreboardAction action) {
        this.action = action;
    }

    @Override
    public WrappedPacketDataSerializer buildData() {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        serializer.serializeString(this.playerName)
                .serializeEnum(this.action)
                .serializeString(this.objectiveName);
        if (action != ScoreboardAction.REMOVE) {
            serializer.serializeVarInt(this.value);
        }
        return serializer;
    }

    @Override
    public Object buildPacket() {
        PacketPlayOutScoreboardScore packet = new PacketPlayOutScoreboardScore();
        try {
            packet.a((PacketDataSerializer) buildData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packet;
    }

}
