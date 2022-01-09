package com.pepedevs.corelib.nms.objects;

import org.bukkit.ChatColor;

import java.util.Collection;
import java.util.Set;

public abstract class WrappedScoreboardTeam {

    public abstract String getName();
    public abstract void setDisplayName(String displayName);
    public abstract String getDisplayName();
    public abstract void setPrefix(String prefix);
    public abstract String getPrefix();
    public abstract void setSuffix(String suffix);
    public abstract String getSuffix();
    public abstract void setPlayerNames(Collection<String> names);
    public abstract Set<String> getPlayerNames();
    public abstract void setPacketOptionData(PacketOptionData optionData);
    public abstract PacketOptionData getOptionData();
    public abstract void setNameTagVisibility(TagVisibility visibility);
    public abstract TagVisibility getNameTagVisibility();
    public abstract void setChatColorFormat(ChatColor color);
    public abstract ChatColor getChatColorFormat();

    public enum PacketOptionData {
        NONE,
        FRIENDLY_FIRE,
        FRIENDLY_CAN_SEE_INVISIBLE,
        ALL;
    }

    public enum TagVisibility {
        ALWAYS,
        NEVER,
        HIDE_FOR_OTHER_TEAMS,
        HIDE_FOR_OWN_TEAM;
    }

}
