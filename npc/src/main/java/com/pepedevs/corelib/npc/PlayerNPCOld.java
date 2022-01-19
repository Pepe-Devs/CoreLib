package com.pepedevs.corelib.npc;

import com.mojang.authlib.GameProfile;
import com.pepedevs.corelib.nms.*;
import com.pepedevs.corelib.nms.objects.WrappedDataWatcher;
import com.pepedevs.corelib.nms.objects.WrappedPlayerInfoData;
import com.pepedevs.corelib.nms.packets.*;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("unused")
public class PlayerNPCOld {

    private static final PacketProvider PACKET_PROVIDER = NMSBridge.getPacketProvider();
    private static final NMSProvider NMS_PROVIDER = NMSBridge.getNMSProvider();
    private static final String NPC_TEAM = "0000_NPC";

    private final WrappedDataWatcher dataWatcher;
    private int entityID;

    private Location location;
    private Vector direction;

    private GameProfile gameProfile;
    private WrappedPlayerInfoData info;
    private boolean isInTab;

    private PlayerNPCData playerNPCData;
    private SkinData skinData;

    public PlayerNPCOld() {
        UUID uuid = new UUID(ThreadLocalRandom.current().nextLong(), 0);
        String name = uuid.toString().substring(0, 8);
        this.gameProfile = new GameProfile(uuid, name);
        this.info = NMS_PROVIDER.getPlayerInfo(this.gameProfile,
                0, EnumGameMode.CREATIVE, name);
        this.entityID = 1500000000 + ThreadLocalRandom.current().nextInt(600000000);
        this.dataWatcher = NMS_PROVIDER.getDataWatcher();
        String teamName = NPC_TEAM + name;
        this.isInTab = false;

        WrappedPacketPlayOutScoreboardTeam scoreboardTeamDestroyPacket = PACKET_PROVIDER.getNewScoreboardTeamPacket(
                teamName,
                Component.empty(),
                Component.empty(),
                Component.empty(),
                Collections.singletonList(name),
                WrappedPacketPlayOutScoreboardTeam.TeamMode.REMOVE,
                WrappedPacketPlayOutScoreboardTeam.TagVisibility.NEVER,
                WrappedPacketPlayOutScoreboardTeam.PacketOptionData.NONE,
                ChatColor.RESET
        );
        WrappedPacketPlayOutScoreboardTeam scoreboardTeamCreatePacket = PACKET_PROVIDER.getNewScoreboardTeamPacket(
                teamName,
                Component.empty(),
                Component.empty(),
                Component.empty(),
                Collections.singletonList(name),
                WrappedPacketPlayOutScoreboardTeam.TeamMode.CREATE,
                WrappedPacketPlayOutScoreboardTeam.TagVisibility.NEVER,
                WrappedPacketPlayOutScoreboardTeam.PacketOptionData.NONE,
                ChatColor.RESET
        );
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            NMSPlayer player = NMS_PROVIDER.getPlayer(onlinePlayer);
            player.sendPacket(scoreboardTeamDestroyPacket);
            player.sendPacket(scoreboardTeamCreatePacket);
        }
        this.skinData = new SkinData();
        this.playerNPCData = new PlayerNPCData();
    }

    public PlayerNPCOld spawn(Location location) {
        this.location = location;
        if (!this.isInTab) {
            this.sendTabAddPacket();
            this.isInTab = true;
        }
        this.sendSpawnPacket();
        return this;
    }

    public PlayerNPCOld teleport(Location location) {
        this.sendTeleportPacket();
        this.location = location;
        return this;
    }

    public PlayerNPCOld look(Vector vector) {
        this.direction = vector;
        return this;
    }

    public PlayerNPCOld updateSkin() {
        if (this.isInTab) {
            this.sendTabRemovePacket();
            this.isInTab = false;
        }
        this.sendDestroyPacket();
        this.entityID = 1500000000 + ThreadLocalRandom.current().nextInt(600000000);
        this.sendTabAddPacket();
        this.isInTab = true;
        this.sendSpawnPacket();
        return this;
    }

    public SkinData getSkinData() {
        return skinData;
    }

    public void setSkinData(SkinData skinData) {
        this.skinData = skinData;
    }

    public PlayerNPCData getPlayerNPCData() {
        return playerNPCData;
    }

    public void setPlayerNPCData(PlayerNPCData playerNPCData) {
        this.playerNPCData = playerNPCData;
    }

    public GameProfile getGameProfile() {
        return gameProfile;
    }

    public void setGameProfile(GameProfile gameProfile) {
        this.gameProfile = gameProfile;
    }

    public WrappedPlayerInfoData getInfo() {
        return this.info;
    }

    public void setInfo(WrappedPlayerInfoData info) {
        this.info = info;
    }

    private void sendPacket(WrappedPacket packet) {
        if (this.location == null || this.location.getWorld() == null) return;
        for (Player player : this.location.getWorld().getPlayers()) {
            NMS_PROVIDER.getPlayer(player).sendPacket(packet);
        }
    }

    public void sendTabAddPacket() {
        WrappedPacket packet = PACKET_PROVIDER.getNewPlayerInfoPacket(
                WrappedPacketPlayOutPlayerInfo.PlayerInfoAction.ADD_PLAYER,
                Collections.singletonList(this.info));
        this.sendPacket(packet);
    }

    public void sendTabRemovePacket() {
        WrappedPacket packet = PACKET_PROVIDER.getNewPlayerInfoPacket(
                WrappedPacketPlayOutPlayerInfo.PlayerInfoAction.REMOVE_PLAYER,
                Collections.singletonList(info));
        this.sendPacket(packet);
    }

    public void sendSpawnPacket() {
        WrappedPacket packet = PACKET_PROVIDER.getNewNamedEntitySpawnPacket(
                this.entityID, this.gameProfile.getId(), this.location, this.dataWatcher
        );
        this.sendPacket(packet);
    }

    public void sendUpdatePlayerNPCData() {
        WrappedPacketPlayOutEntityMetadata packet = PACKET_PROVIDER.getNewEntityMetadataPacket(this.entityID,
                Collections.singletonList(
                    NMS_PROVIDER.getWatchableObject(WrappedDataWatcher.DataTypeId.BYTE.getId(), 0, this.playerNPCData.buildByte())
                ));
        this.sendPacket(packet);
    }

    public void sendUpdateSkinData() {
        WrappedPacketPlayOutEntityMetadata packet = PACKET_PROVIDER.getNewEntityMetadataPacket(this.entityID,
                Collections.singletonList(
                        NMS_PROVIDER.getWatchableObject(WrappedDataWatcher.DataTypeId.BYTE.getId(), 10, this.skinData.buildByte())));
        this.sendPacket(packet);
    }

    public void sendTeleportPacket() {
        WrappedPacketPlayOutEntityTeleport teleport = PACKET_PROVIDER.getNewEntityTeleportPacket(
                this.entityID, location, true);
        this.sendPacket(teleport);
    }

    public void sendDestroyPacket() {
        WrappedPacketPlayOutEntityDestroy packet = PACKET_PROVIDER.getNewEntityDestroyPacket(this.entityID);
        this.sendPacket(packet);
    }
}
