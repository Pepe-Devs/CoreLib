package com.pepedevs.radium.nms.v1_12_R1.packets;

import com.pepedevs.radium.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.radium.nms.packets.WrappedPacketPlayOutExperience;
import com.pepedevs.radium.nms.v1_12_R1.NMSProviderImpl;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutExperience;

import java.io.IOException;

public class WrappedPacketPlayOutExperienceImpl implements WrappedPacketPlayOutExperience {

    private float xp;
    private int totalXp;
    private int level;

    public WrappedPacketPlayOutExperienceImpl(float xp, int totalXp, int level) {
        this.xp = xp;
        this.totalXp = totalXp;
        this.level = level;
    }

    public WrappedPacketPlayOutExperienceImpl(WrappedPacketDataSerializer serializer) {
        PacketDataSerializer dataSerializer = (PacketDataSerializer) serializer;
        this.xp = dataSerializer.readFloat();
        this.level = dataSerializer.g();
        this.totalXp = dataSerializer.g();
    }

    @Override
    public float getXp() {
        return xp;
    }

    @Override
    public int getTotalXp() {
        return totalXp;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setXp(float xp) {
        this.xp = xp;
    }

    @Override
    public void setTotalXp(int totalXp) {
        this.totalXp = totalXp;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public WrappedPacketDataSerializer buildData() {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        serializer.serializeFloat(this.xp).serializeVarInt(this.level).serializeVarInt(this.totalXp);
        return serializer;
    }

    @Override
    public Object buildPacket() {
        PacketPlayOutExperience packet = new PacketPlayOutExperience();
        try {
            packet.a((PacketDataSerializer) buildData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packet;
    }

}
