package com.pepedevs.corelib.nms.v1_8_R3.objects;

import com.pepedevs.corelib.nms.objects.WrappedScoreboardTeam;
import net.minecraft.server.v1_8_R3.EnumChatFormat;
import net.minecraft.server.v1_8_R3.Scoreboard;
import net.minecraft.server.v1_8_R3.ScoreboardTeam;
import net.minecraft.server.v1_8_R3.ScoreboardTeamBase;
import org.bukkit.ChatColor;

import java.util.Collection;
import java.util.Set;

public class WrappedScoreboardTeamImpl extends WrappedScoreboardTeam {

    private final ScoreboardTeam team;

    public WrappedScoreboardTeamImpl(String teamName) {
        this.team = new ScoreboardTeam(new Scoreboard(), teamName);
    }

    @Override
    public String getName() {
        return this.team.getName();
    }

    @Override
    public void setDisplayName(String displayName) {
        this.team.setDisplayName(displayName);
    }

    @Override
    public String getDisplayName() {
        return this.team.getDisplayName();
    }

    @Override
    public void setPrefix(String prefix) {
        this.team.setPrefix(prefix);
    }

    @Override
    public String getPrefix() {
        return this.team.getPrefix();
    }

    @Override
    public void setSuffix(String suffix) {
        this.team.setSuffix(suffix);
    }

    @Override
    public String getSuffix() {
        return this.team.getSuffix();
    }

    @Override
    public void setPlayerNames(Collection<String> names) {
        this.team.getPlayerNameSet().addAll(names);
    }

    @Override
    public Set<String> getPlayerNames() {
        return (Set<String>) this.team.getPlayerNameSet();
    }

    @Override
    public void setPacketOptionData(PacketOptionData optionData) {
        this.team.setAllowFriendlyFire(optionData == WrappedScoreboardTeam.PacketOptionData.FRIENDLY_FIRE || optionData == WrappedScoreboardTeam.PacketOptionData.ALL);
        this.team.setCanSeeFriendlyInvisibles(optionData == WrappedScoreboardTeam.PacketOptionData.FRIENDLY_CAN_SEE_INVISIBLE || optionData == WrappedScoreboardTeam.PacketOptionData.ALL);
    }

    @Override
    public PacketOptionData getOptionData() {
        return PacketOptionData.values()[this.team.packOptionData()];
    }

    @Override
    public void setNameTagVisibility(TagVisibility visibility) {
        this.team.setNameTagVisibility(ScoreboardTeamBase.EnumNameTagVisibility.valueOf(visibility.name()));
    }

    @Override
    public TagVisibility getNameTagVisibility() {
        return TagVisibility.valueOf(this.team.getNameTagVisibility().name());
    }

    @Override
    public void setChatColorFormat(ChatColor color) {
        this.team.a(EnumChatFormat.valueOf(color.name()));
    }

    @Override
    public ChatColor getChatColorFormat() {
        return ChatColor.valueOf(this.team.l().name());
    }
}
