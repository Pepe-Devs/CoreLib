package com.pepedevs.corelib.utils.reflection;

import com.pepedevs.corelib.utils.reflection.resolver.minecraft.NMSClassResolver;
import com.pepedevs.corelib.utils.reflection.resolver.wrapper.ClassWrapper;

public class PacketConstant {

    public static final ClassWrapper<?> PACKET_CLASS;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_ANIMATION;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_ATTACH_ENTITY;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_BLOCK_ACTION;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_BLOCK_BREAK;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_BLOCK_BREAK_ANIMATION;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_BLOCK_CHANGE;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_BOSS;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_CHAT;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_CUSTOM_SOUND_EFFECT;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_ENTITY_DESTROY;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_ENTITY_EFFECT;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_ENTITY_EQUIPMENT;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_ENTITY_HEAD_ROTATION;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_ENTITY_METADATA;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_ENTITY_SOUND;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_ENTITY_STATUS;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_ENTITY_TELEPORT;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_ENTITY_VELOCITY;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_ENTITY_EXPERIENCE;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_EXPLOSION;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_HELD_ITEM_SLOT;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_MULTI_BLOCK_CHANGE;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_NAMED_ENTITY_SPAWN;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_PLAYER_INFO;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_PLAYER_LIST_HEADER_FOOTER;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_POSITION;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_REMOVE_ENTITY_EFFECT;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_RESPAWN;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_SCOREBOARD_DISPLAY_OBJECTIVE;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_SCOREBOARD_OBJECTIVE;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_SCOREBOARD_SCORE;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_SCOREBOARD_TEAM;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_SPAWN_ENTITY;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_SPAWN_ENTITY_EXPERIENCE_ORB;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_SPAWN_ENTITY_LIVING;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_STATISTIC;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_TAB_COMPLETE;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_UPDATE_HEALTH;
    public static final ClassWrapper<?> PACKET_PLAY_OUT_UPDATE_TIME;

    static {
        NMSClassResolver nmsClassResolver = new NMSClassResolver();
        String packetPackageV17 = "net.minecraft.network.protocol.game.";
        PACKET_CLASS = nmsClassResolver.resolveWrapper("Packet", "net.minecraft.network.protocol.Packet");
        PACKET_PLAY_OUT_ANIMATION = nmsClassResolver.resolveWrapper("PacketPlayOutAnimation", packetPackageV17 + "PacketPlayOutAnimation");
        PACKET_PLAY_OUT_ATTACH_ENTITY = nmsClassResolver.resolveWrapper("PacketPlayOutAttachEntity", packetPackageV17 + "PacketPlayOutAttachEntity");
        PACKET_PLAY_OUT_BLOCK_ACTION = nmsClassResolver.resolveWrapper("PacketPlayOutBlockAction", packetPackageV17 + "PacketPlayOutBlockAction");
        PACKET_PLAY_OUT_BLOCK_BREAK = nmsClassResolver.resolveWrapper("PacketPlayOutBlockBreak", packetPackageV17 + "PacketPlayOutBlockBreak");
        PACKET_PLAY_OUT_BLOCK_BREAK_ANIMATION = nmsClassResolver.resolveWrapper("PacketPlayOutBlockBreakAnimation", packetPackageV17 + "PacketPlayOutBlockBreakAnimation");
        PACKET_PLAY_OUT_BLOCK_CHANGE = nmsClassResolver.resolveWrapper("PacketPlayOutBlockChange", packetPackageV17 + "PacketPlayOutBlockChange");
        PACKET_PLAY_OUT_BOSS = nmsClassResolver.resolveWrapper("PacketPlayOutBoss", packetPackageV17 + "PacketPlayOutBoss");
        PACKET_PLAY_OUT_CHAT = nmsClassResolver.resolveWrapper("PacketPlayOutChat", packetPackageV17 + "PacketPlayOutChat");
        PACKET_PLAY_OUT_CUSTOM_SOUND_EFFECT = nmsClassResolver.resolveWrapper("PacketPlayOutCustomSoundEffect", packetPackageV17 + "PacketPlayOutCustomSoundEffect");
        PACKET_PLAY_OUT_ENTITY_DESTROY = nmsClassResolver.resolveWrapper("PacketPlayOutEntityDestroy", packetPackageV17 + "PacketPlayOutEntityDestroy");
        PACKET_PLAY_OUT_ENTITY_EFFECT = nmsClassResolver.resolveWrapper("PacketPlayOutEntitySound", packetPackageV17 + "PacketPlayOutEntitySound");
        PACKET_PLAY_OUT_ENTITY_EQUIPMENT = nmsClassResolver.resolveWrapper("PacketPlayOutEntityEquipment", packetPackageV17 + "PacketPlayOutEntityEquipment");
        PACKET_PLAY_OUT_ENTITY_HEAD_ROTATION = nmsClassResolver.resolveWrapper("PacketPlayOutEntityHeadRotation", packetPackageV17 + "PacketPlayOutEntityHeadRotation");
        PACKET_PLAY_OUT_ENTITY_METADATA = nmsClassResolver.resolveWrapper("PacketPlayOutEntityMetadata", packetPackageV17 + "PacketPlayOutEntityMetadata");
        PACKET_PLAY_OUT_ENTITY_SOUND = nmsClassResolver.resolveWrapper("PacketPlayOutEntitySound", packetPackageV17 + "PacketPlayOutEntitySound");
        PACKET_PLAY_OUT_ENTITY_STATUS = nmsClassResolver.resolveWrapper("PacketPlayOutEntityStatus", packetPackageV17 + "PacketPlayOutEntityStatus");
        PACKET_PLAY_OUT_ENTITY_TELEPORT = nmsClassResolver.resolveWrapper("PacketPlayOutEntityTeleport", packetPackageV17 + "PacketPlayOutEntityTeleport");
        PACKET_PLAY_OUT_ENTITY_VELOCITY = nmsClassResolver.resolveWrapper("PacketPlayOutEntityVelocity", packetPackageV17 + "PacketPlayOutEntityVelocity");
        PACKET_PLAY_OUT_ENTITY_EXPERIENCE = nmsClassResolver.resolveWrapper("PacketPlayOutExperience", packetPackageV17 + "PacketPlayOutExperience");
        PACKET_PLAY_OUT_EXPLOSION = nmsClassResolver.resolveWrapper("PacketPlayOutExplosion", packetPackageV17 + "PacketPlayOutExplosion");
        PACKET_PLAY_OUT_HELD_ITEM_SLOT = nmsClassResolver.resolveWrapper("PacketPlayOutHeldItemSlot", packetPackageV17 + "PacketPlayOutHeldItemSlot");
        PACKET_PLAY_OUT_MULTI_BLOCK_CHANGE = nmsClassResolver.resolveWrapper("PacketPlayOutMultiBlockChange", packetPackageV17 + "PacketPlayOutMultiBlockChange");
        PACKET_PLAY_OUT_NAMED_ENTITY_SPAWN = nmsClassResolver.resolveWrapper("PacketPlayOutNamedEntitySpawn", packetPackageV17 + "PacketPlayOutNamedEntitySpawn");
        PACKET_PLAY_OUT_PLAYER_INFO = nmsClassResolver.resolveWrapper("PacketPlayOutMultiBlockChange", packetPackageV17 + "PacketPlayOutMultiBlockChange");
        PACKET_PLAY_OUT_PLAYER_LIST_HEADER_FOOTER = nmsClassResolver.resolveWrapper("PacketPlayOutPlayerListHeaderFooter", packetPackageV17 + "PacketPlayOutPlayerListHeaderFooter");
        PACKET_PLAY_OUT_POSITION = nmsClassResolver.resolveWrapper("PacketPlayOutPosition", packetPackageV17 + "PacketPlayOutPosition");
        PACKET_PLAY_OUT_REMOVE_ENTITY_EFFECT = nmsClassResolver.resolveWrapper("PacketPlayOutRemoveEntityEffect", packetPackageV17 + "PacketPlayOutRemoveEntityEffect");
        PACKET_PLAY_OUT_RESPAWN = nmsClassResolver.resolveWrapper("PacketPlayOutRespawn", packetPackageV17 + "PacketPlayOutRespawn");
        PACKET_PLAY_OUT_SCOREBOARD_DISPLAY_OBJECTIVE = nmsClassResolver.resolveWrapper("PacketPlayOutScoreboardDisplayObjective", packetPackageV17 + "PacketPlayOutScoreboardDisplayObjective");
        PACKET_PLAY_OUT_SCOREBOARD_OBJECTIVE = nmsClassResolver.resolveWrapper("PacketPlayOutScoreboardObjective", packetPackageV17 + "PacketPlayOutScoreboardObjective");
        PACKET_PLAY_OUT_SCOREBOARD_SCORE = nmsClassResolver.resolveWrapper("PacketPlayOutScoreboardScore", packetPackageV17 + "PacketPlayOutScoreboardScore");
        PACKET_PLAY_OUT_SCOREBOARD_TEAM = nmsClassResolver.resolveWrapper("PacketPlayOutScoreboardTeam", packetPackageV17 + "PacketPlayOutScoreboardTeam");
        PACKET_PLAY_OUT_SPAWN_ENTITY = nmsClassResolver.resolveWrapper("PacketPlayOutSpawnEntity", packetPackageV17 + "PacketPlayOutSpawnEntity");
        PACKET_PLAY_OUT_SPAWN_ENTITY_EXPERIENCE_ORB = nmsClassResolver.resolveWrapper("PacketPlayOutSpawnEntityExperienceOrb", packetPackageV17 + "PacketPlayOutSpawnEntityExperienceOrb");
        PACKET_PLAY_OUT_SPAWN_ENTITY_LIVING = nmsClassResolver.resolveWrapper("PacketPlayOutSpawnEntityLiving", packetPackageV17 + "PacketPlayOutSpawnEntityLiving");
        PACKET_PLAY_OUT_STATISTIC = nmsClassResolver.resolveWrapper("PacketPlayOutStatistic", packetPackageV17 + "PacketPlayOutStatistic");
        PACKET_PLAY_OUT_TAB_COMPLETE = nmsClassResolver.resolveWrapper("PacketPlayOutTabComplete", packetPackageV17 + "PacketPlayOutTabComplete");
        PACKET_PLAY_OUT_UPDATE_HEALTH = nmsClassResolver.resolveWrapper("PacketPlayOutUpdateHealth", packetPackageV17 + "PacketPlayOutUpdateHealth");
        PACKET_PLAY_OUT_UPDATE_TIME = nmsClassResolver.resolveWrapper("PacketPlayOutUpdateTime", packetPackageV17 + "PacketPlayOutUpdateTime");
    }

}
