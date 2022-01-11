package com.pepedevs.corelib.nms.v1_8_R3.packets;

import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutExperience;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutExperience;

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
