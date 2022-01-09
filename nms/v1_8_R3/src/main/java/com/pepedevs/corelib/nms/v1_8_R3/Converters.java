package com.pepedevs.corelib.nms.v1_8_R3;

import com.pepedevs.corelib.nms.InventoryType;
import net.minecraft.server.v1_8_R3.EnumChatFormat;
import net.minecraft.server.v1_8_R3.Scoreboard;
import net.minecraft.server.v1_8_R3.ScoreboardTeam;
import net.minecraft.server.v1_8_R3.ScoreboardTeamBase;

public class Converters {

    public static String convertInventory(InventoryType inventoryType) {
        return "minecraft:".concat(inventoryType.name().toLowerCase());
    }

}
