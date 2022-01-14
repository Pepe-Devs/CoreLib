package com.pepedevs.corelib.npc;

import com.pepedevs.corelib.utils.math.Vector;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public interface NPC {

    void spawn();

    void teleport(Location location);

    Location getLocation();

    void lookAt(Vector direction);

    void destroy();

    Set<UUID> getViewers();

    void hide(Player player);

    void show(Player player);

}
