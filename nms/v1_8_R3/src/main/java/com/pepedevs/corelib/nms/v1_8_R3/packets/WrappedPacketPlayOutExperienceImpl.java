package com.pepedevs.corelib.nms.v1_8_R3.packets;

import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutExperience;
import com.pepedevs.corelib.nms.v1_8_R3.NMSProviderImpl;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutExperience;

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
        this.level = dataSerializer.e();
        this.totalXp = dataSerializer.e();
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
