package com.pepedevs.corelib.npc;

import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.protocol.player.GameMode;
import com.github.retrooper.packetevents.protocol.player.GameProfile;
import com.github.retrooper.packetevents.protocol.player.TextureProperty;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfo;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnPlayer;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerTeams;
import com.pepedevs.corelib.npc.internal.NpcBase;
import com.pepedevs.corelib.utils.Skin;
import com.pepedevs.corelib.utils.version.Version;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerNPC extends NpcBase {

    private final GameProfile gameProfile;
    private final SkinData skinData;

    public PlayerNPC(Location location) {
        super(location);
        this.gameProfile = new GameProfile(this.getUuid(), "");
        this.skinData = new SkinData();
    }

    public void setSkin(Skin skin) {
        this.gameProfile.getTextureProperties().clear();
        this.gameProfile.getTextureProperties().add(
                new TextureProperty("textures", skin.getValue(), skin.getSignature())
        );
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
        WrapperPlayServerPlayerInfo.PlayerData info = new WrapperPlayServerPlayerInfo.PlayerData(
                Component.empty(),
                this.gameProfile,
                GameMode.CREATIVE,
                0
        );
        WrapperPlayServerPlayerInfo infoAddPacket = new WrapperPlayServerPlayerInfo(
                WrapperPlayServerPlayerInfo.Action.ADD_PLAYER,
                info
        );
        WrapperPlayServerSpawnPlayer spawnPacket = new WrapperPlayServerSpawnPlayer(
                this.entityId,
                this.uuid,
                super.convert(this.location),
                this.location.getYaw(),
                this.location.getPitch(),
                new ArrayList<>()
        );
        WrapperPlayServerPlayerInfo infoRemovePacket = new WrapperPlayServerPlayerInfo(
                WrapperPlayServerPlayerInfo.Action.REMOVE_PLAYER,
                info
        );
        PACKET_EVENTS_API.getPlayerManager().sendPacket(player, infoAddPacket);
        PACKET_EVENTS_API.getPlayerManager().sendPacket(player, spawnPacket);
        //ADD DELAY
        PACKET_EVENTS_API.getPlayerManager().sendPacket(player, infoRemovePacket);
    }

    public void updateSkin(Skin skin) {
        this.gameProfile.getTextureProperties().clear();
        this.gameProfile.getTextureProperties().add(new TextureProperty("textures", skin.getValue(), skin.getSignature()));
        for (UUID uuid : this.shown) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) continue;
            this.destroy(player);
            this.view(player);
        }
    }

    public void hideNameTag() {
        WrapperPlayServerTeams packet = new WrapperPlayServerTeams("NPC_0000" + this.uuid.toString().substring(0, 8),
                WrapperPlayServerTeams.TeamMode.REMOVE,
                Collections.singletonList(this.gameProfile.getName()),
                Optional.empty());
        for (UUID uuid : this.shown) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) continue;
            PACKET_EVENTS_API.getPlayerManager().sendPacket(player, packet);
        }
    }

    public void showNameTag() {
        WrapperPlayServerTeams packet = new WrapperPlayServerTeams("NPC_0000" + this.uuid.toString().substring(0, 8),
                WrapperPlayServerTeams.TeamMode.CREATE,
                Collections.singletonList(this.gameProfile.getName()),
                Optional.empty());
        for (UUID uuid : this.shown) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) continue;
            PACKET_EVENTS_API.getPlayerManager().sendPacket(player, packet);
        }
    }

    public void showSkinParts(SkinPart... skinParts) {
        for (SkinPart part : skinParts) {
            switch (part) {
                case CAPE: {
                    this.skinData.setCapeEnabled(true);
                    continue;
                }
                case JACKET: {
                    this.skinData.setJacketEnabled(true);
                    continue;
                }
                case LEFT_SLEEVE: {
                    this.skinData.setLeftSleeveEnabled(true);
                    continue;
                }
                case RIGHT_SLEEVE: {
                    this.skinData.setRightSleeveEnabled(true);
                    continue;
                }
                case LEFT_PANTS: {
                    this.skinData.setLeftPantsLegEnabled(true);
                    continue;
                }
                case RIGHT_PANTS: {
                    this.skinData.setRightPantsLegEnabled(true);
                    continue;
                }
                case HAT_ENABLED: {
                    this.skinData.setHatEnabled(true);
                }
            }
            this.updateSkinData();
        }
    }

    public void hideSkinParts(SkinPart... skinParts) {
        for (SkinPart part : skinParts) {
            switch (part) {
                case CAPE: {
                    this.skinData.setCapeEnabled(false);
                    continue;
                }
                case JACKET: {
                    this.skinData.setJacketEnabled(false);
                    continue;
                }
                case LEFT_SLEEVE: {
                    this.skinData.setLeftSleeveEnabled(false);
                    continue;
                }
                case RIGHT_SLEEVE: {
                    this.skinData.setRightSleeveEnabled(false);
                    continue;
                }
                case LEFT_PANTS: {
                    this.skinData.setLeftPantsLegEnabled(false);
                    continue;
                }
                case RIGHT_PANTS: {
                    this.skinData.setRightPantsLegEnabled(false);
                    continue;
                }
                case HAT_ENABLED: {
                    this.skinData.setHatEnabled(false);
                }
            }
            this.updateSkinData();
        }
    }

    public SkinPart[] getShownSkinParts() {
        List<SkinPart> shownSkinParts = new ArrayList<>();
        if (this.skinData.isCapeEnabled()) shownSkinParts.add(SkinPart.CAPE);
        if (this.skinData.isJacketEnabled()) shownSkinParts.add(SkinPart.JACKET);
        if (this.skinData.isLeftSleeveEnabled()) shownSkinParts.add(SkinPart.LEFT_SLEEVE);
        if (this.skinData.isRightSleeveEnabled()) shownSkinParts.add(SkinPart.RIGHT_SLEEVE);
        if (this.skinData.isLeftPantsLegEnabled()) shownSkinParts.add(SkinPart.LEFT_PANTS);
        if (this.skinData.isRightPantsLegEnabled()) shownSkinParts.add(SkinPart.RIGHT_PANTS);
        if (this.skinData.isHatEnabled()) shownSkinParts.add(SkinPart.HAT_ENABLED);
        return shownSkinParts.toArray(new SkinPart[0]);
    }

    private void updateSkinData() {
        EntityData entityData = new EntityData(10, EntityDataTypes.BYTE, this.skinData.buildByte());
        switch (Version.SERVER_VERSION) {
            case v1_8_R1:
            case v1_8_R2:
            case v1_8_R3: {
                entityData.setIndex(10);
                break;
            }
            case v1_9_R1:
            case v1_9_R2: {
                entityData.setIndex(12);
                break;
            }
            case v1_10_R1:
            case v1_11_R1:
            case v1_12_R1:
            case v1_13_R1:
            case v1_13_R2:
            case v1_14_R1: {
                entityData.setIndex(13);
                break;
            }
            case v1_15_R1: {
                entityData.setIndex(15);
            }
            case v1_16_R1:
            case v1_16_R2:
            case v1_16_R3:{
                entityData.setIndex(16);
            }
            case v1_17_R1:
            case v1_18_R1: {
                entityData.setIndex(17);
            }
        }
        WrapperPlayServerEntityMetadata packet = new WrapperPlayServerEntityMetadata(this.entityId,
                Collections.singletonList(entityData)
        );
        for (UUID uuid : this.shown) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) continue;
            PACKET_EVENTS_API.getPlayerManager().sendPacket(player, packet);
        }
    }

    enum SkinPart {
        CAPE,
        JACKET,
        LEFT_SLEEVE,
        RIGHT_SLEEVE,
        LEFT_PANTS,
        RIGHT_PANTS,
        HAT_ENABLED
    }


}
