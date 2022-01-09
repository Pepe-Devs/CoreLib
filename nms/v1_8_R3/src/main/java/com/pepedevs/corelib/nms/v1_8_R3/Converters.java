package com.pepedevs.corelib.nms.v1_8_R3;

import com.pepedevs.corelib.nms.InventoryType;
import com.pepedevs.corelib.nms.objects.WrappedScoreboardTeam;
import net.minecraft.server.v1_8_R3.EnumChatFormat;
import net.minecraft.server.v1_8_R3.Scoreboard;
import net.minecraft.server.v1_8_R3.ScoreboardTeam;
import net.minecraft.server.v1_8_R3.ScoreboardTeamBase;

public class Converters {

    public static String convertInventory(InventoryType inventoryType) {
        return "minecraft:".concat(inventoryType.name().toLowerCase());
    }

    public static ScoreboardTeam getTeam(WrappedScoreboardTeam team) {
        net.minecraft.server.v1_8_R3.ScoreboardTeam scoreboardTeam = new ScoreboardTeam(new Scoreboard(), team.getName());
        scoreboardTeam.setDisplayName(team.getDisplayName());
        scoreboardTeam.setPrefix(team.getPrefix());
        scoreboardTeam.setSuffix(team.getSuffix());
        scoreboardTeam.getPlayerNameSet().addAll(team.getPlayerNames());
        WrappedScoreboardTeam.PacketOptionData optionData = team.getOptionData();
        scoreboardTeam.setAllowFriendlyFire(optionData == WrappedScoreboardTeam.PacketOptionData.FRIENDLY_FIRE || optionData == WrappedScoreboardTeam.PacketOptionData.ALL);
        scoreboardTeam.setCanSeeFriendlyInvisibles(optionData == WrappedScoreboardTeam.PacketOptionData.FRIENDLY_CAN_SEE_INVISIBLE || optionData == WrappedScoreboardTeam.PacketOptionData.ALL);
        scoreboardTeam.setNameTagVisibility(ScoreboardTeamBase.EnumNameTagVisibility.valueOf(team.getNameTagVisibility().name()));
        scoreboardTeam.a(EnumChatFormat.valueOf(team.getChatColorFormat().name()));
        return scoreboardTeam;
    }

}
