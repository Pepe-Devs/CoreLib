package com.pepedevs.corelib.holograms.action;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface HologramClickAction {

    void onClick(Player player, ClickType clickType);
}
