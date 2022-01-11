package com.pepedevs.corelib.nms.v1_12_R1.packets;

import com.pepedevs.corelib.adventure.AdventureUtils;
import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutScoreboardTeam;
import com.pepedevs.corelib.nms.v1_12_R1.NMSProviderImpl;
import net.kyori.adventure.text.Component;
import net.minecraft.server.v1_12_R1.EnumChatFormat;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutScoreboardTeam;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_12_R1.util.CraftChatMessage;

import java.io.IOException;
import java.util.Set;

public class WrappedPacketPlayOutScoreboardTeamImpl extends PacketPlayOutScoreboardTeam implements WrappedPacketPlayOutScoreboardTeam {

    public WrappedPacketPlayOutScoreboardTeamImpl(String teamName, String displayName, String prefix, String suffix, Set<String> playerNames, TeamMode mode, TagVisibility visibility, PacketOptionData data, ChatColor color) {
        WrappedPacketDataSerializer dataSerializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        dataSerializer.serializeString(teamName)
                .serializeByte(mode.ordinal());
        if (mode == TeamMode.CREATE || mode == TeamMode.UPDATE) {
            dataSerializer.serializeString(displayName)
                    .serializeString(prefix)
                    .serializeString(suffix)
                    .serializeByte(data.ordinal())
                    .serializeString(visibility.ID)
                    .serializeByte(((EnumChatFormat) NMSProviderImpl.INSTANCE.getEnumChatFormat(color)).b());
        }

        if (mode == TeamMode.CREATE || mode == TeamMode.ADD_PLAYERS || mode == TeamMode.REMOVE_PLAYERS) {
            dataSerializer.serializeIntToByte(playerNames.size());
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

    public WrappedPacketPlayOutScoreboardTeamImpl(String teamName, Component displayName, Component prefix, Component suffix, Set<String> playerNames, TeamMode mode, TagVisibility visibility, PacketOptionData data, ChatColor color) {
        this(teamName, AdventureUtils.toVanillaString(displayName), AdventureUtils.toVanillaString(prefix), AdventureUtils.toVanillaString(suffix), playerNames, mode, visibility, data, color);
    }

    public WrappedPacketPlayOutScoreboardTeamImpl(String teamName, Object displayName, Object prefix, Object suffix, Set<String> playerNames, TeamMode mode, TagVisibility visibility, PacketOptionData data, ChatColor color) {
        this(teamName, CraftChatMessage.fromComponent((IChatBaseComponent) displayName), CraftChatMessage.fromComponent((IChatBaseComponent) prefix), CraftChatMessage.fromComponent((IChatBaseComponent) suffix), playerNames, mode, visibility, data, color);
    }

    public WrappedPacketPlayOutScoreboardTeamImpl(WrappedPacketDataSerializer dataSerializer) {
        try {
            this.a((PacketDataSerializer) dataSerializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
