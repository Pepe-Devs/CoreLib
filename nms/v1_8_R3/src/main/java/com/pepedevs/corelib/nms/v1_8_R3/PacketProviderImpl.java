package com.pepedevs.corelib.nms.v1_8_R3;

import com.pepedevs.corelib.nms.*;
import com.pepedevs.corelib.nms.objects.WrappedPlayerInfoData;
import com.pepedevs.corelib.nms.packets.*;
import com.pepedevs.corelib.nms.v1_8_R3.packets.*;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class PacketProviderImpl implements PacketProvider {

    public static final PacketProviderImpl INSTANCE = new PacketProviderImpl();

    @Override
    public WrappedPacketPlayOutAttachEntity getNewAttachEntityPacket(WrappedPacketPlayOutAttachEntity.AttachmentType type, int riderID, int providerID) {
        return new WrappedPacketPlayOutAttachEntityImpl(type, riderID, providerID);
    }

    @Override
    public WrappedPacketPlayOutAttachEntity getNewAttachEntityPacket(WrappedPacketPlayOutAttachEntity.AttachmentType type, int riderID) {
        return new WrappedPacketPlayOutAttachEntityImpl(type, riderID);
    }

    @Override
    public WrappedPacketPlayOutChat getNewChatPacket(String chatMessage) {
        return new WrappedPacketPlayOutChatImpl(chatMessage);
    }

    @Override
    public WrappedPacketPlayOutChat getNewChatPacket(Component chatMessage) {
        return new WrappedPacketPlayOutChatImpl(chatMessage);
    }

    @Override
    public WrappedPacketPlayOutChat getNewChatPacket(String chatMessage, WrappedPacketPlayOutChat.ChatMessageType type) {
        return new WrappedPacketPlayOutChatImpl(chatMessage, type);
    }

    @Override
    public WrappedPacketPlayOutChat getNewChatPacket(Component chatMessage, WrappedPacketPlayOutChat.ChatMessageType type) {
        return new WrappedPacketPlayOutChatImpl(chatMessage, type);
    }

    @Override
    public WrappedPacketPlayOutCloseWindow getNewCloseWindowPacket(int containerID) {
        return new WrappedPacketPlayOutCloseWindowImpl(containerID);
    }

    @Override
    public WrappedPacketPlayOutEntityDestroy getNewEntityDestroyPacket(int... entityIDs) {
        return new WrappedPacketPlayOutEntityDestroyImpl(entityIDs);
    }

    @Override
    public WrappedPacketPlayOutEntityEquipment getNewEntityEquipmentPacket(int entityID, ItemSlot slot, ItemStack itemStack) {
        return new WrappedPacketPlayOutEntityEquipmentImpl(entityID, slot, itemStack);
    }

    @Override
    public WrappedPacketPlayOutEntityHeadRotation getNewEntityHeadRotationPacket(int entityId, float headRotation) {
        return new WrappedPacketPlayOutEntityHeadRotationImpl(entityId, headRotation);
    }

    @Override
    public WrappedPacketPlayOutEntityMetadata getNewEntityMetadataPacket(int entityID, Object watchableObjects) {
        return new WrappedPacketPlayOutEntityMetadataImpl(entityID, watchableObjects);
    }

    @Override
    public WrappedPacketPlayOutEntityStatus getNewEntityStatusPacket(int entityID, EntityStatus status) {
        return new WrappedPacketPlayOutEntityStatusImpl(entityID, status);
    }

    @Override
    public WrappedPacketPlayOutEntityStatus getNewEntityStatusPacket(int entityID, byte status) {
        return new WrappedPacketPlayOutEntityStatusImpl(entityID, status);
    }

    @Override
    public WrappedPacketPlayOutEntityTeleport getNewEntityTeleportPacket(int entityId, int locX, int locY, int locZ, float yaw, float pitch) {
        return new WrappedPacketPlayOutEntityTeleportImpl(entityId, locX, locY, locZ, yaw, pitch);
    }

    @Override
    public WrappedPacketPlayOutEntityTeleport getNewEntityTeleportPacket(int entityId, int locX, int locY, int locZ, float yaw, float pitch, boolean onGround) {
        return new WrappedPacketPlayOutEntityTeleportImpl(entityId, locX, locY, locZ, yaw, pitch, onGround);
    }

    @Override
    public WrappedPacketPlayOutEntityTeleport getNewEntityTeleportPacket(int entityID, Location location) {
        return new WrappedPacketPlayOutEntityTeleportImpl(entityID, location);
    }

    @Override
    public WrappedPacketPlayOutEntityTeleport getNewEntityTeleportPacket(int entityID, Location location, boolean onGround) {
        return new WrappedPacketPlayOutEntityTeleportImpl(entityID, location, onGround);
    }

    @Override
    public WrappedPacketPlayOutEntityVelocity getNewEntityVelocityPacket(int entityID, Vector vector) {
        return new WrappedPacketPlayOutEntityVelocityImpl(entityID, vector);
    }

    @Override
    public WrappedPacketPlayOutExperience getNewExperiencePacket(float xp, int totalXp, int level) {
        return new WrappedPacketPlayOutExperienceImpl(xp, totalXp, level);
    }

    @Override
    public WrappedPacketPlayOutExplosion getNewExplosionPacket(Vector location, float power) {
        return new WrappedPacketPlayOutExplosionImpl(location, power);
    }

    @Override
    public WrappedPacketPlayOutExplosion getNewExplosionPacket(Vector location, float power, Set<Block> blocks, Vector knockback) {
        return new WrappedPacketPlayOutExplosionImpl(location, power, blocks, knockback);
    }

    @Override
    public WrappedPacketPlayOutExplosion getNewExplosionPacket(Vector location, float power, List<Vector> blocks, Vector knockback) {
        return new WrappedPacketPlayOutExplosionImpl(location, power, blocks, knockback);
    }

    @Override
    public WrappedPacketPlayOutHeldItemSlot getNewHeldItemSlotPacket(int slot) {
        return new WrappedPacketPlayOutHeldItemSlotImpl(slot);
    }

    @Override
    public WrappedPacketPlayOutNamedEntitySpawn getNewNamedEntitySpawnPacket(int entityId, UUID uuid, double locX, double locY, double locZ, float yaw, float pitch, Object dataWatcher) {
        return new WrappedPacketPlayOutNamedEntitySpawnImpl(entityId, uuid, locX, locY, locZ, yaw, pitch, dataWatcher);
    }

    @Override
    public WrappedPacketPlayOutNamedEntitySpawn getNewNamedEntitySpawnPacket(int entityId, UUID uuid, Location location, Object dataWatcher) {
        return new WrappedPacketPlayOutNamedEntitySpawnImpl(entityId, uuid, location, dataWatcher);
    }

    @Override
    public WrappedPacketPlayOutOpenSignEditor getNewOpenSignEditorPacket(Location location) {
        return new WrappedPacketPlayOutOpenSignEditorImpl(location);
    }

    @Override
    public WrappedPacketPlayOutOpenSignEditor getNewOpenSignEditorPacket(Vector location) {
        return new WrappedPacketPlayOutOpenSignEditorImpl(location);
    }

    @Override
    public WrappedPacketPlayOutOpenWindow getNewOpenWindowPacket(int nextContainerCounter, InventoryType type, String title) {
        return new WrappedPacketPlayOutOpenWindowImpl(nextContainerCounter, title, type);
    }

    @Override
    public WrappedPacketPlayOutOpenWindow getNewOpenWindowPacket(int nextContainerCounter, Component title, InventoryType type) {
        return new WrappedPacketPlayOutOpenWindowImpl(nextContainerCounter, title, type);
    }

    @Override
    public WrappedPacketPlayOutOpenWindow getNewOpenWindowPacket(int nextContainerCounter, String title, InventoryType type, int rows) {
        return new WrappedPacketPlayOutOpenWindowImpl(nextContainerCounter, title, type, rows);
    }

    @Override
    public WrappedPacketPlayOutOpenWindow getNewOpenWindowPacket(int nextContainerCounter, Component title, InventoryType type, int rows) {
        return new WrappedPacketPlayOutOpenWindowImpl(nextContainerCounter, title, type, rows);
    }

    @Override
    public WrappedPacketPlayOutPlayerInfo getNewPlayerInfoPacket(WrappedPacketPlayOutPlayerInfo.PlayerInfoAction action, List<WrappedPlayerInfoData> data) {
        return new WrappedPacketPlayOutPlayerInfoImpl(action, data);
    }

    @Override
    public WrappedPacketPlayOutPlayerListHeaderFooter getNewPlayerListHeaderFooterPacket(String header, String footer) {
        return new WrappedPacketPlayOutPlayerListHeaderFooterImpl(header, footer);
    }

    @Override
    public WrappedPacketPlayOutPlayerListHeaderFooter getNewPlayerListHeaderFooterPacket(Component header, Component footer) {
        return new WrappedPacketPlayOutPlayerListHeaderFooterImpl(header, footer);
    }

    @Override
    public WrappedPacketPlayOutPlayerListHeaderFooter getNewPlayerListHeaderFooterPacket(Object header, Object footer) {
        return new WrappedPacketPlayOutPlayerListHeaderFooterImpl(header, footer);
    }

    @Override
    public WrappedPacketPlayOutRespawn getNewRespawnPacket(World world, Difficulty difficulty, EnumGameMode gameMode) {
        return new WrappedPacketPlayOutRespawnImpl(world, difficulty, gameMode);
    }

    @Override
    public WrappedPacketPlayOutRespawn getNewRespawnPacket(World world, Difficulty difficulty, EnumGameMode gameMode, WorldType type) {
        return new WrappedPacketPlayOutRespawnImpl(world, difficulty, gameMode, type);
    }

    @Override
    public WrappedPacketPlayOutScoreboardDisplayObjective getNewScoreboardDisplayObjectivePacket(WrappedPacketPlayOutScoreboardDisplayObjective.ScoreboardPosition position, String name) {
        return new WrappedPacketPlayOutScoreboardDisplayObjectiveImpl(position, name);
    }

    @Override
    public WrappedPacketPlayOutScoreboardDisplayObjective getNewScoreboardDisplayObjectivePacket(int position, String name) {
        return new WrappedPacketPlayOutScoreboardDisplayObjectiveImpl(position, name);
    }

    @Override
    public WrappedPacketPlayOutScoreboardObjective getNewScoreboardObjectivePacket(String name, String displayName, WrappedPacketPlayOutScoreboardObjective.HealthDisplay display, WrappedPacketPlayOutScoreboardObjective.ObjectiveMode objectiveMode) {
        return new WrappedPacketPlayOutScoreboardObjectiveImpl(name, displayName, display, objectiveMode);
    }

    @Override
    public WrappedPacketPlayOutScoreboardScore getNewScoreboardScorePacket(String playerName, String objectiveName, int value, WrappedPacketPlayOutScoreboardScore.ScoreboardAction action) {
        return new WrappedPacketPlayOutScoreboardScoreImpl(playerName, objectiveName, value, action);
    }

    @Override
    public WrappedPacketPlayOutScoreboardTeam getNewScoreboardTeamPacket(String teamName, String displayName, String prefix, String suffix, List<String> playerNames, WrappedPacketPlayOutScoreboardTeam.TeamMode mode, WrappedPacketPlayOutScoreboardTeam.TagVisibility visibility, WrappedPacketPlayOutScoreboardTeam.PacketOptionData data, ChatColor color) {
        return new WrappedPacketPlayOutScoreboardTeamImpl(teamName, displayName, prefix, suffix, playerNames, mode, visibility, data, color);
    }

    @Override
    public WrappedPacketPlayOutScoreboardTeam getNewScoreboardTeamPacket(String teamName, Component displayName, Component prefix, Component suffix, List<String> playerNames, WrappedPacketPlayOutScoreboardTeam.TeamMode mode, WrappedPacketPlayOutScoreboardTeam.TagVisibility visibility, WrappedPacketPlayOutScoreboardTeam.PacketOptionData data, ChatColor color) {
        return new WrappedPacketPlayOutScoreboardTeamImpl(teamName, displayName, prefix, suffix, playerNames, mode, visibility, data, color);
    }

    @Override
    public WrappedPacketPlayOutScoreboardTeam getNewScoreboardTeamPacket(String teamName, Object displayName, Object prefix, Object suffix, List<String> playerNames, WrappedPacketPlayOutScoreboardTeam.TeamMode mode, WrappedPacketPlayOutScoreboardTeam.TagVisibility visibility, WrappedPacketPlayOutScoreboardTeam.PacketOptionData data, ChatColor color) {
        return new WrappedPacketPlayOutScoreboardTeamImpl(teamName, displayName, prefix, suffix, playerNames, mode, visibility, data, color);
    }

    @Override
    public WrappedPacketPlayOutSpawnEntityLiving getNewSpawnEntityLivingPacket(int entityId, UUID uuid, EntityType entityType, double locX, double locY, double locZ, float yaw, float pitch, float headRotation, Vector velocity, Object dataWatcher) {
        return new WrappedPacketPlayOutSpawnEntityLivingImpl(entityId, uuid, entityType, locX, locY, locZ, yaw, pitch, headRotation, velocity, dataWatcher);
    }

    @Override
    public WrappedPacketPlayOutSpawnEntityLiving getNewSpawnEntityLivingPacket(int entityId, UUID uuid, EntityType entity, Location location, float headPitch, Vector velocity, Object dataWatcher) {
        return new WrappedPacketPlayOutSpawnEntityLivingImpl(entityId, uuid, entity, location, headPitch, velocity, dataWatcher);
    }

    @Override
    public WrappedPacketPlayOutSpawnEntityLiving getNewSpawnEntityLivingPacket(int entityId, UUID uuid, EntityType entity, double locX, double locY, double locZ, float yaw, float pitch, float headPitch, Object dataWatcher) {
        return new WrappedPacketPlayOutSpawnEntityLivingImpl(entityId, uuid, entity, locX, locY, locZ, yaw, pitch, headPitch, dataWatcher);
    }

    @Override
    public WrappedPacketPlayOutSpawnEntityLiving getNewSpawnEntityLivingPacket(int entityId, UUID uuid, EntityType entity, Location location, float headPitch, Object dataWatcher) {
        return new WrappedPacketPlayOutSpawnEntityLivingImpl(entityId, uuid, entity, location, headPitch, dataWatcher);
    }

    @Override
    public WrappedPacketPlayOutTabComplete getNewTabCompletePacket(String... completions) {
        return new WrappedPacketPlayOutTabCompleteImpl(completions);
    }

    @Override
    public WrappedPacketPlayOutUpdateSign getNewUpdateSignPacket(Vector location, String... lines) {
        return new WrappedPacketPlayOutUpdateSignImpl(location, lines);
    }

    @Override
    public WrappedPacketPlayOutUpdateSign getNewUpdateSignPacket(Vector location, Component... lines) {
        return new WrappedPacketPlayOutUpdateSignImpl(location, lines);
    }

    @Override
    public WrappedPacketPlayOutUpdateSign getNewUpdateSignPacket(Vector location, Object... lines) {
        return new WrappedPacketPlayOutUpdateSignImpl(location, lines);
    }

}
