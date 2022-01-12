package com.pepedevs.corelib.nms.v1_12_R1.packets;

import com.pepedevs.corelib.adventure.AdventureUtils;
import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutScoreboardTeam;
import com.pepedevs.corelib.nms.v1_12_R1.NMSProviderImpl;
import net.kyori.adventure.text.Component;
import net.minecraft.server.v1_12_R1.EnumChatFormat;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutScoreboardTeam;
import org.bukkit.ChatColor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WrappedPacketPlayOutScoreboardTeamImpl implements WrappedPacketPlayOutScoreboardTeam {

    private String teamName;
    private Component displayName;
    private Component prefix;
    private Component suffix;
    private List<String> playerNames;
    private TeamMode mode;
    private TagVisibility tagVisibility;
    private PacketOptionData data;
    private ChatColor color;

    public WrappedPacketPlayOutScoreboardTeamImpl(String teamName, String displayName, String prefix, String suffix, List<String> playerNames, TeamMode mode, TagVisibility visibility, PacketOptionData data, ChatColor color) {
        this(teamName, AdventureUtils.fromVanillaString(displayName), AdventureUtils.fromVanillaString(prefix), AdventureUtils.fromVanillaString(suffix), playerNames, mode, visibility, data, color);
    }

    public WrappedPacketPlayOutScoreboardTeamImpl(String teamName, Component displayName, Component prefix, Component suffix, List<String> playerNames, TeamMode mode, TagVisibility visibility, PacketOptionData data, ChatColor color) {
        this.teamName = teamName;
        this.displayName = displayName;
        this.prefix = prefix;
        this.suffix = suffix;
        this.playerNames = playerNames;
        this.mode = mode;
        this.tagVisibility = visibility;
        this.data = data;
        this.color = color;
    }

    public WrappedPacketPlayOutScoreboardTeamImpl(String teamName, Object displayName, Object prefix, Object suffix, List<String> playerNames, TeamMode mode, TagVisibility visibility, PacketOptionData data, ChatColor color) {
        this(teamName, AdventureUtils.asAdventure(displayName), AdventureUtils.asAdventure(prefix), AdventureUtils.asAdventure(suffix), playerNames, mode, visibility, data, color);
    }

    public WrappedPacketPlayOutScoreboardTeamImpl(WrappedPacketDataSerializer serializer) {
        PacketDataSerializer dataSerializer = (PacketDataSerializer) serializer;
        this.teamName = dataSerializer.e(16);
        this.mode = TeamMode.values()[dataSerializer.readByte()];
        if (this.mode == TeamMode.CREATE || this.mode == TeamMode.UPDATE) {
            this.displayName = AdventureUtils.fromVanillaString(dataSerializer.e(32));
            this.prefix = AdventureUtils.fromVanillaString(dataSerializer.e(16));
            this.suffix = AdventureUtils.fromVanillaString(dataSerializer.e(16));
            this.data = PacketOptionData.values()[dataSerializer.readByte()];
            this.tagVisibility = TagVisibility.valueOf(dataSerializer.e(32));
            this.color = NMSProviderImpl.INSTANCE.getEnumChatFormat(EnumChatFormat.a(dataSerializer.readByte()));
        }

        this.playerNames = new ArrayList<>();
        if (this.mode == TeamMode.CREATE || this.mode == TeamMode.ADD_PLAYERS || this.mode == TeamMode.REMOVE_PLAYERS) {
            int size = dataSerializer.g();

            for(int i = 0; i < size; ++i) {
                this.playerNames.add(dataSerializer.e(40));
            }
        }
    }

    @Override
    public String getTeamName() {
        return teamName;
    }

    @Override
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public Component getDisplayName() {
        return displayName;
    }

    @Override
    public void setDisplayName(Component displayName) {
        this.displayName = displayName;
    }

    @Override
    public Component getPrefix() {
        return prefix;
    }

    @Override
    public void setPrefix(Component prefix) {
        this.prefix = prefix;
    }

    @Override
    public Component getSuffix() {
        return suffix;
    }

    @Override
    public void setSuffix(Component suffix) {
        this.suffix = suffix;
    }

    @Override
    public List<String> getPlayerNames() {
        return playerNames;
    }

    @Override
    public void setPlayerNames(List<String> playerNames) {
        this.playerNames = playerNames;
    }

    @Override
    public TeamMode getMode() {
        return mode;
    }

    @Override
    public void setMode(TeamMode mode) {
        this.mode = mode;
    }

    @Override
    public TagVisibility getTagVisibility() {
        return tagVisibility;
    }

    @Override
    public void setTagVisibility(TagVisibility tagVisibility) {
        this.tagVisibility = tagVisibility;
    }

    @Override
    public PacketOptionData getData() {
        return data;
    }

    @Override
    public void setData(PacketOptionData data) {
        this.data = data;
    }

    @Override
    public ChatColor getColor() {
        return color;
    }

    @Override
    public void setColor(ChatColor color) {
        this.color = color;
    }

    @Override
    public WrappedPacketDataSerializer buildData() {
        WrappedPacketDataSerializer dataSerializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        dataSerializer.serializeString(this.teamName)
                .serializeByte(this.mode.ordinal());
        if (this.mode == TeamMode.CREATE || this.mode == TeamMode.UPDATE) {
            dataSerializer.serializeString(AdventureUtils.toVanillaString(this.displayName))
                    .serializeString(AdventureUtils.toVanillaString(this.prefix))
                    .serializeString(AdventureUtils.toVanillaString(this.suffix))
                    .serializeByte(this.data.ordinal())
                    .serializeString(this.tagVisibility.ID)
                    .serializeByte(((EnumChatFormat) NMSProviderImpl.INSTANCE.getEnumChatFormat(this.color)).b());
        }

        if (this.mode == TeamMode.CREATE || this.mode == TeamMode.ADD_PLAYERS || this.mode == TeamMode.REMOVE_PLAYERS) {
            dataSerializer.serializeIntToByte(this.playerNames.size());
            for (String playerName : this.playerNames) {
                dataSerializer.serializeString(playerName);
            }
        }
        return dataSerializer;
    }

    @Override
    public Object buildPacket() {
        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();
        try {
            packet.a((PacketDataSerializer) buildData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packet;
    }

}
