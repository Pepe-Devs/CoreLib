package com.pepedevs.corelib.nms;

import com.pepedevs.corelib.nms.objects.WrappedPlayerInfoData;
import com.pepedevs.corelib.nms.packets.*;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface PacketProvider {

    WrappedPacketPlayOutAttachEntity getNewAttachEntityPacket(WrappedPacketPlayOutAttachEntity.AttachmentType type, int riderID, int providerID);

    WrappedPacketPlayOutAttachEntity getNewAttachEntityPacket(WrappedPacketPlayOutAttachEntity.AttachmentType type, int riderID);

    WrappedPacketPlayOutChat getNewChatPacket(String chatMessage);

    WrappedPacketPlayOutChat getNewChatPacket(Component chatMessage);

    WrappedPacketPlayOutChat getNewChatPacket(String chatMessage, WrappedPacketPlayOutChat.ChatMessageType type);

    WrappedPacketPlayOutChat getNewChatPacket(Component chatMessage, WrappedPacketPlayOutChat.ChatMessageType type);

    WrappedPacketPlayOutCloseWindow getNewCloseWindowPacket(int containerID);

    WrappedPacketPlayOutEntityDestroy getNewEntityDestroyPacket(int... entityIDs);

    WrappedPacketPlayOutEntityEquipment getNewEntityEquipmentPacket(int entityID, ItemSlot slot, ItemStack itemStack);

    WrappedPacketPlayOutEntityHeadRotation getNewEntityHeadRotationPacket(int entityId, float headRotation);

    WrappedPacketPlayOutEntityMetadata getNewEntityMetadataPacket(int entityID, Object watchableObjects);

    WrappedPacketPlayOutEntityStatus getNewEntityStatusPacket(int entityID, EntityStatus status);

    WrappedPacketPlayOutEntityStatus getNewEntityStatusPacket(int entityID, byte status);

    WrappedPacketPlayOutEntityTeleport getNewEntityTeleportPacket(int entityId, int locX, int locY, int locZ, float yaw, float pitch);

    WrappedPacketPlayOutEntityTeleport getNewEntityTeleportPacket(int entityId, int locX, int locY, int locZ, float yaw, float pitch, boolean onGround);

    WrappedPacketPlayOutEntityTeleport getNewEntityTeleportPacket(int entityID, Location location);

    WrappedPacketPlayOutEntityTeleport getNewEntityTeleportPacket(int entityID, Location location, boolean onGround);

    WrappedPacketPlayOutEntityVelocity getNewEntityVelocityPacket(int entityID, Vector vector);

    WrappedPacketPlayOutExperience getNewExperiencePacket(float xp, int totalXp, int level);

    WrappedPacketPlayOutExplosion getNewExplosionPacket(Vector location, float power);

    WrappedPacketPlayOutExplosion getNewExplosionPacket(Vector location, float power, Set<Block> blocks, Vector knockback);

    WrappedPacketPlayOutExplosion getNewExplosionPacket(Vector location, float power, List<Vector> blocks, Vector knockback);

    WrappedPacketPlayOutHeldItemSlot getNewHeldItemSlotPacket(int slot);

    WrappedPacketPlayOutNamedEntitySpawn getNewNamedEntitySpawnPacket(int entityId, UUID uuid, double locX, double locY, double locZ, float yaw, float pitch, Object dataWatcher);

    WrappedPacketPlayOutNamedEntitySpawn getNewNamedEntitySpawnPacket(int entityId, UUID uuid, Location location, Object dataWatcher);

    WrappedPacketPlayOutOpenSignEditor getNewOpenSignEditorPacket(Location location);

    WrappedPacketPlayOutOpenSignEditor getNewOpenSignEditorPacket(Vector location);

    WrappedPacketPlayOutOpenWindow getNewOpenWindowPacket(int nextContainerCounter, InventoryType type, String title);

    WrappedPacketPlayOutOpenWindow getNewOpenWindowPacket(int nextContainerCounter, Component title, InventoryType type);

    WrappedPacketPlayOutOpenWindow getNewOpenWindowPacket(int nextContainerCounter, String title, InventoryType type, int rows);

    WrappedPacketPlayOutOpenWindow getNewOpenWindowPacket(int nextContainerCounter, Component title, InventoryType type, int rows);

    WrappedPacketPlayOutPlayerInfo getNewPlayerInfoPacket(WrappedPacketPlayOutPlayerInfo.PlayerInfoAction action, List<WrappedPlayerInfoData> data);

    WrappedPacketPlayOutPlayerListHeaderFooter getNewPlayerListHeaderFooterPacket(String header, String footer);

    WrappedPacketPlayOutPlayerListHeaderFooter getNewPlayerListHeaderFooterPacket(Component header, Component footer);

    WrappedPacketPlayOutPlayerListHeaderFooter getNewPlayerListHeaderFooterPacket(Object header, Object footer);

    WrappedPacketPlayOutRespawn getNewRespawnPacket(World world, Difficulty difficulty, EnumGameMode gameMode);

    WrappedPacketPlayOutRespawn getNewRespawnPacket(World world, Difficulty difficulty, EnumGameMode gameMode, WorldType type);

    WrappedPacketPlayOutScoreboardDisplayObjective getNewScoreboardDisplayObjectivePacket(WrappedPacketPlayOutScoreboardDisplayObjective.ScoreboardPosition position, String name);

    WrappedPacketPlayOutScoreboardDisplayObjective getNewScoreboardDisplayObjectivePacket(int position, String name);

    WrappedPacketPlayOutScoreboardObjective getNewScoreboardObjectivePacket(String name, String displayName, WrappedPacketPlayOutScoreboardObjective.HealthDisplay display, WrappedPacketPlayOutScoreboardObjective.ObjectiveMode objectiveMode);

    WrappedPacketPlayOutScoreboardScore getNewScoreboardScorePacket(String playerName, String objectiveName, int value, WrappedPacketPlayOutScoreboardScore.ScoreboardAction action);

    WrappedPacketPlayOutScoreboardTeam getNewScoreboardTeamPacket(String teamName, String displayName, String prefix, String suffix, List<String> playerNames, WrappedPacketPlayOutScoreboardTeam.TeamMode mode, WrappedPacketPlayOutScoreboardTeam.TagVisibility visibility, WrappedPacketPlayOutScoreboardTeam.PacketOptionData data, ChatColor color);

    WrappedPacketPlayOutScoreboardTeam getNewScoreboardTeamPacket(String teamName, Component displayName, Component prefix, Component suffix, List<String> playerNames, WrappedPacketPlayOutScoreboardTeam.TeamMode mode, WrappedPacketPlayOutScoreboardTeam.TagVisibility visibility, WrappedPacketPlayOutScoreboardTeam.PacketOptionData data, ChatColor color);

    WrappedPacketPlayOutScoreboardTeam getNewScoreboardTeamPacket(String teamName, Object displayName, Object prefix, Object suffix, List<String> playerNames, WrappedPacketPlayOutScoreboardTeam.TeamMode mode, WrappedPacketPlayOutScoreboardTeam.TagVisibility visibility, WrappedPacketPlayOutScoreboardTeam.PacketOptionData data, ChatColor color);

    WrappedPacketPlayOutSpawnEntityLiving getNewSpawnEntityLivingPacket(int entityId, UUID uuid, EntityType entityType, double locX, double locY, double locZ, float yaw, float pitch, float headRotation, Vector velocity, Object dataWatcher);

    WrappedPacketPlayOutSpawnEntityLiving getNewSpawnEntityLivingPacket(int entityId, UUID uuid, EntityType entity, Location location, float headPitch, Vector velocity, Object dataWatcher);

    WrappedPacketPlayOutSpawnEntityLiving getNewSpawnEntityLivingPacket(int entityId, UUID uuid, EntityType entity, double locX, double locY, double locZ, float yaw, float pitch, float headPitch, Object dataWatcher);

    WrappedPacketPlayOutSpawnEntityLiving getNewSpawnEntityLivingPacket(int entityId, UUID uuid, EntityType entity, Location location, float headPitch, Object dataWatcher);

    WrappedPacketPlayOutTabComplete getNewTabCompletePacket(String... completions);

    WrappedPacketPlayOutUpdateSign getNewUpdateSignPacket(Vector location, String... lines);

    WrappedPacketPlayOutUpdateSign getNewUpdateSignPacket(Vector location, Component... lines);

    WrappedPacketPlayOutUpdateSign getNewUpdateSignPacket(Vector location, Object... lines);

}
