package com.pepedevs.corelib.npc.action;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface NPCClickAction {

    void onClick(Player player, ClickType clickType);

}
