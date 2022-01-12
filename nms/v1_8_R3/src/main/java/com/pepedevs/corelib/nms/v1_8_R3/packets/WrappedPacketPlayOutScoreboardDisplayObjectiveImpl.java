package com.pepedevs.corelib.nms.v1_8_R3.packets;

import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutScoreboardDisplayObjective;
import com.pepedevs.corelib.nms.v1_8_R3.NMSProviderImpl;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardDisplayObjective;

import java.io.IOException;

public class WrappedPacketPlayOutScoreboardDisplayObjectiveImpl implements WrappedPacketPlayOutScoreboardDisplayObjective {

    private int position;
    private String name;

    public WrappedPacketPlayOutScoreboardDisplayObjectiveImpl(ScoreboardPosition position, String name) {
        this.position = position.ordinal();
        this.name = name;
    }

    public WrappedPacketPlayOutScoreboardDisplayObjectiveImpl(int position, String name) {
        this.position = position;
        this.name = name;
    }

    public WrappedPacketPlayOutScoreboardDisplayObjectiveImpl(WrappedPacketDataSerializer serializer) {
        PacketDataSerializer dataSerializer = (PacketDataSerializer) serializer;
        this.position = dataSerializer.readByte();
        this.name = dataSerializer.c(16);
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public ScoreboardPosition getPositionEnum() {
        return ScoreboardPosition.values()[position];
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void setPosition(ScoreboardPosition position) {
        this.position = position.ordinal();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public WrappedPacketDataSerializer buildData() {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        serializer.serializeByte(this.position);
        if (name == null) {
            serializer.serializeString("");
        } else {
            serializer.serializeString(this.name);
        }
        return serializer;
    }

    @Override
    public Object buildPacket() {
        PacketPlayOutScoreboardDisplayObjective packet = new PacketPlayOutScoreboardDisplayObjective();
        try {
            packet.a((PacketDataSerializer) buildData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packet;
    }

}
