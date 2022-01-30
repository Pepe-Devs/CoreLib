package com.pepedevs.radium.npc;

import com.pepedevs.radium.npc.action.NPCClickAction;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

public interface NPC {

    int getEntityId();

    void setEntityId(int id);

    String getId();

    void spawn();

    void teleport(Location location);

    Location getLocation();

    void look(float yaw, float pitch);

    void lookAt(Vector direction);

    void lookAt(Location location);

    void destroy();

    Set<UUID> getViewers();

    void hide(Player player);

    void silentHide(Player player);

    void show(Player player);

    void forceShow(Player player);

    void addInShownList(Player player);

    Collection<NPCClickAction> getClickActions();

    void addClickAction(NPCClickAction action);

}
