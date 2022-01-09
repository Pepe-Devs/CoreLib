package com.pepedevs.corelib.nms.packets;

public interface WrappedPacketPlayOutScoreboardTeam extends WrappedPacket {

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
    }

}
