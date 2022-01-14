import com.mojang.authlib.GameProfile;
import com.pepedevs.corelib.nms.EnumGameMode;
import com.pepedevs.corelib.nms.NMSBridge;
import com.pepedevs.corelib.nms.NMSProvider;
import com.pepedevs.corelib.nms.PacketProvider;
import com.pepedevs.corelib.nms.objects.WrappedDataWatcher;
import com.pepedevs.corelib.nms.objects.WrappedPlayerInfoData;
import com.pepedevs.corelib.nms.packets.*;
import com.pepedevs.corelib.utils.version.Version;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerNPC {

    private static final PacketProvider PACKET_PROVIDER = NMSBridge.getPacketProvider();
    private static final NMSProvider NMS_PROVIDER = NMSBridge.getNMSProvider();
    private static final String NPC_TEAM = "0000_NPC";

    private final String name;
    private final String teamName;
    private final WrappedDataWatcher dataWatcher;
    private final int entityID;

    private Component displayName;
    private Location location;
    private Vector direction;

    private GameProfile gameProfile;
    private WrappedPlayerInfoData info;
    private boolean isInTab;

    private PlayerNPCData playerNPCData;
    private SkinData skinData;

    public PlayerNPC(Component displayName) {
        UUID uuid = new UUID(ThreadLocalRandom.current().nextLong(), 0);
        this.name = uuid.toString().substring(0,8);
        this.gameProfile = new GameProfile(uuid, this.name);
        this.displayName = displayName;
        this.info = NMS_PROVIDER.getPlayerInfo(this.gameProfile,
                0, EnumGameMode.CREATIVE, this.displayName);
        this.entityID = 1500000000 + ThreadLocalRandom.current().nextInt(600000000);
        this.dataWatcher = NMS_PROVIDER.getDataWatcher();
        this.teamName = NPC_TEAM + this.name;
        this.isInTab = false;

        WrappedPacketPlayOutScoreboardTeam scoreboardTeamPacket = PACKET_PROVIDER.getNewScoreboardTeamPacket(
                this.teamName,
                Component.empty(),
                Component.empty(),
                Component.empty(),
                Collections.singletonList(this.gameProfile.getName()),
                WrappedPacketPlayOutScoreboardTeam.TeamMode.CREATE,
                WrappedPacketPlayOutScoreboardTeam.TagVisibility.NEVER,
                WrappedPacketPlayOutScoreboardTeam.PacketOptionData.NONE,
                ChatColor.RESET
        );
        this.sendPacket(scoreboardTeamPacket);
        this.skinData = new SkinData();
        this.playerNPCData = new PlayerNPCData();
    }

    public PlayerNPC spawn(Location location) {
        this.location = location;
        if (!this.isInTab) {
            this.sendTabAddPacket();
            this.isInTab = true;
        }
        this.sendSpawnPacket();
        return this;
    }

    public PlayerNPC updateDisplayName(Component displayName) {
        WrappedPacketPlayOutScoreboardTeam packet = PACKET_PROVIDER.getNewScoreboardTeamPacket(
                this.teamName,
                Component.empty(),
                displayName,
                Component.empty(),
                Collections.singletonList(this.name),
                WrappedPacketPlayOutScoreboardTeam.TeamMode.UPDATE,
                WrappedPacketPlayOutScoreboardTeam.TagVisibility.ALWAYS,
                WrappedPacketPlayOutScoreboardTeam.PacketOptionData.NONE,
                ChatColor.RESET
        );
        this.sendPacket(packet);
        this.displayName = displayName;
        return this;
    }

    public PlayerNPC teleport(Location location) {
        this.sendTeleportPacket();
        this.location = location;
        return this;
    }

    public PlayerNPC look(Vector vector) {
        this.direction = vector;
        return this;
    }

    public PlayerNPC updateSkin() {
        if (this.isInTab) {
            this.sendTabRemovePacket();
            this.isInTab = false;
        }
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
                    NMS_PROVIDER.getWatchableObject(WrappedDataWatcher.DataTypeId.BYTE.getId(), 10, this.playerNPCData.buildByte())
                ));
        this.sendPacket(packet);
    }

    public void sendUpdateSkinData() {
        WrappedPacketPlayOutEntityMetadata packet = PACKET_PROVIDER.getNewEntityMetadataPacket(this.entityID,
                Collections.singletonList(
                        NMS_PROVIDER.getWatchableObject(WrappedDataWatcher.DataTypeId.BYTE.getId(), 0, this.skinData.buildByte())));
        this.sendPacket(packet);
    }

    public void sendTeleportPacket() {
        WrappedPacketPlayOutEntityTeleport teleport = PACKET_PROVIDER.getNewEntityTeleportPacket(
                this.entityID, location, true);
        this.sendPacket(teleport);
    }
}
