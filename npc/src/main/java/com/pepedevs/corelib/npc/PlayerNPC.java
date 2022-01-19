package com.pepedevs.corelib.npc;

import com.mojang.authlib.GameProfile;
import com.pepedevs.corelib.nms.EnumGameMode;
import com.pepedevs.corelib.nms.NMSPlayer;
import com.pepedevs.corelib.nms.objects.WrappedPlayerInfoData;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutNamedEntitySpawn;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutPlayerInfo;
import com.pepedevs.corelib.npc.internal.NpcBase;
import com.pepedevs.corelib.utils.Skin;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.UUID;

public class PlayerNPC extends NpcBase {

    private final GameProfile gameProfile;

    public PlayerNPC(Location location) {
        super(location);
        this.gameProfile = new GameProfile(this.getUuid(), "");
    }

    public void setSkin(Skin skin) {
        this.gameProfile.getProperties().clear();
        this.gameProfile.getProperties().put("textures", skin.asProperty());
        for (UUID uuid : this.shown) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null)
                continue;
            this.hide(player);
            this.view(player);
        }
    }

    @Override
    protected void view(Player player) {
        WrappedPlayerInfoData info = NMS_PROVIDER.getPlayerInfo(this.gameProfile, 0, EnumGameMode.CREATIVE, Component.empty());
        WrappedPacketPlayOutPlayerInfo infoAddPacket = PACKET_PROVIDER.getNewPlayerInfoPacket(WrappedPacketPlayOutPlayerInfo.PlayerInfoAction.ADD_PLAYER, Collections.singletonList(info));
        WrappedPacketPlayOutNamedEntitySpawn namedSpawn = PACKET_PROVIDER.getNewNamedEntitySpawnPacket(this.entityId, this.uuid, this.location, NMS_PROVIDER.getDataWatcher());
        WrappedPacketPlayOutPlayerInfo infoRemovePacket = PACKET_PROVIDER.getNewPlayerInfoPacket(WrappedPacketPlayOutPlayerInfo.PlayerInfoAction.REMOVE_PLAYER, Collections.singletonList(info));
        NMSPlayer p = NMS_PROVIDER.getPlayer(player);
        p.sendPacket(infoAddPacket);
        p.sendPacket(namedSpawn);
        // Add delay
        p.sendPacket(infoRemovePacket);
    }

}
