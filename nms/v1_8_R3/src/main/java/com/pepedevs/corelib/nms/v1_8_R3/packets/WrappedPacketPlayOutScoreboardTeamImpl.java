package com.pepedevs.corelib.nms.v1_8_R3.packets;

import com.pepedevs.corelib.nms.packets.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutScoreboardTeam;
import com.pepedevs.corelib.nms.v1_8_R3.NMSImpl;
import net.minecraft.server.v1_8_R3.EnumChatFormat;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import org.bukkit.ChatColor;

import java.io.IOException;
import java.util.Set;

public class WrappedPacketPlayOutScoreboardTeamImpl extends PacketPlayOutScoreboardTeam implements WrappedPacketPlayOutScoreboardTeam {

    public WrappedPacketPlayOutScoreboardTeamImpl(String teamName, String displayName, String prefix, String suffix, Set<String> playerNames, TeamMode mode, TagVisibility visibility, PacketOptionData data, ChatColor color) {
        WrappedPacketDataSerializer dataSerializer = NMSImpl.INSTANCE.getDataSerializer();
        dataSerializer.serializeString(teamName);
        dataSerializer.serializeByte(mode.ordinal());
        if (mode == TeamMode.CREATE || mode == TeamMode.UPDATE) {
            dataSerializer.serializeString(displayName);
            dataSerializer.serializeString(prefix);
            dataSerializer.serializeString(suffix);
            dataSerializer.serializeByte(data.ordinal());
            dataSerializer.serializeString(visibility.ID);
            dataSerializer.serializeByte(EnumChatFormat.valueOf(color.name()).b());
        }

        if (mode == TeamMode.CREATE || mode == TeamMode.ADD_PLAYERS || mode == TeamMode.REMOVE_PLAYERS) {
            dataSerializer.serializeSize(playerNames.size());
            for (String playerName : playerNames) {
                dataSerializer.serializeString(playerName);
            }
        }

        try {
            this.a((PacketDataSerializer) dataSerializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WrappedPacketPlayOutScoreboardTeamImpl(WrappedPacketDataSerializer dataSerializer) {
        try {
            this.a((PacketDataSerializer) dataSerializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
