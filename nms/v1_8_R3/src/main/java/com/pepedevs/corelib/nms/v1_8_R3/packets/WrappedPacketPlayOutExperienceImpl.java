package com.pepedevs.corelib.nms.v1_8_R3.packets;

import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutExperience;
import net.minecraft.server.v1_8_R3.PacketPlayOutExperience;

public class WrappedPacketPlayOutExperienceImpl extends PacketPlayOutExperience implements WrappedPacketPlayOutExperience {

    public WrappedPacketPlayOutExperienceImpl(float xp, int totalXp, int level) {
        super(xp, totalXp, level);
    }

}
