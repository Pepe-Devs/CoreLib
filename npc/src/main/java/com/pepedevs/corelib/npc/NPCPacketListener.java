package com.pepedevs.corelib.npc;

import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientInteractEntity;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.pepedevs.corelib.npc.action.ClickType;
import com.pepedevs.corelib.npc.action.NPCClickAction;
import com.pepedevs.corelib.npc.internal.NpcBase;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class NPCPacketListener extends PacketListenerAbstract {

    private static final Cache<UUID, Integer> COOLDOWN =
            CacheBuilder.newBuilder().expireAfterWrite(20, TimeUnit.MILLISECONDS).build();

    private final NPCManager npcManager;

    public NPCPacketListener(NPCManager npcManager) {
        super(PacketListenerPriority.MONITOR, false);
        this.npcManager = npcManager;
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.INTERACT_ENTITY) {
            Player player = (Player) event.getPlayer();
            WrapperPlayClientInteractEntity packet = new WrapperPlayClientInteractEntity(event);
            if (COOLDOWN.asMap().containsKey(player.getUniqueId())
                    && COOLDOWN.asMap().getOrDefault(player.getUniqueId(), -1)
                    == packet.getEntityId()) return;
            for (NPC npc : npcManager.getNpcs().values()) {
                if (((NpcBase) npc).getEntityId() == packet.getEntityId()) {
                    if (!npc.getLocation().getWorld().getName().equals(player.getLocation().getWorld().getName())) continue;
                    for (NPCClickAction clickAction : npc.getClickActions()) {
                        clickAction.onClick(player, getClickType(player, packet.getAction()));
                        COOLDOWN.put(player.getUniqueId(), packet.getEntityId());
                    }
                    break;
                }
            }
        }
    }

    private ClickType getClickType(Player player, WrapperPlayClientInteractEntity.InteractAction action) {
        boolean sneak = player.isSneaking();
        ClickType clickType;
        if (action == WrapperPlayClientInteractEntity.InteractAction.ATTACK) {
            clickType = sneak ? ClickType.SHIFT_LEFT_CLICK : ClickType.LEFT_CLICK;
        } else {
            clickType = sneak ? ClickType.SHIFT_RIGHT_CLICK : ClickType.RIGHT_CLICK;
        }
        return clickType;
    }

}
