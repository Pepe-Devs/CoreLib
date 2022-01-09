package com.pepedevs.corelib.nms.v1_8_R3.packets;

import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutScoreboardObjective;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardObjective;
import net.minecraft.server.v1_8_R3.ScoreboardObjective;

public class WrappedPacketPlayOutScoreboardObjectiveImpl extends PacketPlayOutScoreboardObjective implements WrappedPacketPlayOutScoreboardObjective {

    public WrappedPacketPlayOutScoreboardObjectiveImpl(ScoreboardObjective scoreboardObjective, int i) {
        super(scoreboardObjective, i);
    }
}
