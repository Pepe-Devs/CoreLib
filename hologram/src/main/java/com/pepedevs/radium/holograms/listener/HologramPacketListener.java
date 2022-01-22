package com.pepedevs.radium.holograms.listener;

import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientInteractEntity;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.pepedevs.radium.holograms.HologramManager;
import com.pepedevs.radium.holograms.action.ClickType;
import com.pepedevs.radium.holograms.object.Hologram;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class HologramPacketListener extends PacketListenerAbstract {

    private static final Cache<UUID, Integer> COOLDOWN =
            CacheBuilder.newBuilder().expireAfterWrite(20, TimeUnit.MILLISECONDS).build();

    private final HologramManager hologramManager;

    public HologramPacketListener(HologramManager hologramManager) {
        super(PacketListenerPriority.MONITOR, false);
        this.hologramManager = hologramManager;
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.INTERACT_ENTITY) {
            Player player = (Player) event.getPlayer();
            WrapperPlayClientInteractEntity packet = new WrapperPlayClientInteractEntity(event);
            if (COOLDOWN.asMap().containsKey(player.getUniqueId())
                    && COOLDOWN.asMap().getOrDefault(player.getUniqueId(), -1)
                    == packet.getEntityId()) return;
            for (Hologram hologram : this.hologramManager.getHolograms().values()) {
                if (!hologram.getLocation().getWorld().getName().equals(player.getLocation().getWorld().getName())) continue;
                if (hologram.handleClick(player, packet.getEntityId(), this.getClickType(player, packet.getAction()))) {
                    COOLDOWN.put(player.getUniqueId(), packet.getEntityId());
                    break;
                }
            }
        }
    }

    private ClickType getClickType(Player player, WrapperPlayClientInteractEntity.InteractAction action) {
        if (action == WrapperPlayClientInteractEntity.InteractAction.ATTACK) {
            return player.isSneaking() ? ClickType.SHIFT_LEFT_CLICK : ClickType.LEFT_CLICK;
        } else {
            return player.isSneaking() ? ClickType.SHIFT_RIGHT_CLICK : ClickType.RIGHT_CLICK;
        }
    }
}
