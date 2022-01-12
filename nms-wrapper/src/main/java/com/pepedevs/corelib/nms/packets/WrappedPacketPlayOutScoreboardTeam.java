package com.pepedevs.corelib.nms.packets;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;

import java.util.List;

public interface WrappedPacketPlayOutScoreboardTeam extends WrappedPacket {

    String getTeamName();

    void setTeamName(String teamName);

    Component getDisplayName();

    void setDisplayName(Component displayName);

    Component getPrefix();

    void setPrefix(Component prefix);

    Component getSuffix();

    void setSuffix(Component suffix);

    List<String> getPlayerNames();

    void setPlayerNames(List<String> playerNames);

    TeamMode getMode();

    void setMode(TeamMode mode);

    TagVisibility getTagVisibility();

    void setTagVisibility(TagVisibility tagVisibility);

    PacketOptionData getData();

    void setData(PacketOptionData data);

    ChatColor getColor();

    void setColor(ChatColor color);

    enum TeamMode {
        CREATE,
        REMOVE,
        UPDATE,
        ADD_PLAYERS,
        REMOVE_PLAYERS;
    }

    enum PacketOptionData {
        NONE,
        FRIENDLY_FIRE,
        FRIENDLY_CAN_SEE_INVISIBLE,
        ALL;
    }

    enum TagVisibility {
        ALWAYS("always"),
        NEVER("never"),
        HIDE_FOR_OTHER_TEAMS("hideForOtherTeams"),
        HIDE_FOR_OWN_TEAM("hideForOwnTeam");

        public final String ID;

        TagVisibility(String ID) {
            this.ID = ID;
        }

        public static TagVisibility fromId(String id) {
            for (TagVisibility value : TagVisibility.values()) {
                if (value.ID.equalsIgnoreCase(id)) {
                    return value;
                }
            }
            return null;
        }
    }

}
