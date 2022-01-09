package com.pepedevs.corelib.nms.v1_8_R3.packets;

import com.pepedevs.corelib.nms.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.objects.WrappedScoreboardTeam;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutScoreboardTeam;
import com.pepedevs.corelib.nms.v1_8_R3.Converters;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;

import java.util.Set;

public class WrappedPacketPlayOutScoreboardTeamImpl extends PacketPlayOutScoreboardTeam implements WrappedPacketPlayOutScoreboardTeam {

    public WrappedPacketPlayOutScoreboardTeamImpl(WrappedScoreboardTeam scoreboardTeam, TeamMode teamMode) {
        super(Converters.getTeam(scoreboardTeam), teamMode.ordinal());
    }

    public WrappedPacketPlayOutScoreboardTeamImpl(String teamName, String displayName, String prefix, String suffix, Set<String> playerNames, ) {

    }

    public WrappedPacketPlayOutScoreboardTeamImpl(WrappedPacketDataSerializer dataSerializer) {

    }

}
