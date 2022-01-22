package com.pepedevs.radium.holograms.listener;

import com.pepedevs.radium.holograms.HologramManager;
import com.pepedevs.radium.holograms.object.Hologram;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class HologramListener implements Listener {

    private final HologramManager manager;

    public HologramListener(HologramManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        for (Hologram value : this.manager.getHolograms().values()) {
            value.hide(player);
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();
        for (Hologram value : this.manager.getHolograms().values()) {
            value.hide(player);
        }
    }
}
