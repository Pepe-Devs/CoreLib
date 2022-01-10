package com.pepedevs.corelib.nms.v1_12_R1.packets;

import com.pepedevs.corelib.nms.packets.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutExperience;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutExperience;

import java.io.IOException;

public class WrappedPacketPlayOutExperienceImpl extends PacketPlayOutExperience implements WrappedPacketPlayOutExperience {

    public WrappedPacketPlayOutExperienceImpl(float xp, int totalXp, int level) {
        super(xp, totalXp, level);
    }

    public WrappedPacketPlayOutExperienceImpl(WrappedPacketDataSerializer serializer) {
        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
