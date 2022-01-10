package com.pepedevs.corelib.nms.v1_8_R3.packets;

import com.pepedevs.corelib.nms.EnumGameMode;
import com.pepedevs.corelib.nms.packets.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutRespawn;
import com.pepedevs.corelib.nms.v1_8_R3.NMSImpl;
import net.minecraft.server.v1_8_R3.EnumDifficulty;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutRespawn;
import net.minecraft.server.v1_8_R3.WorldSettings;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

import java.io.IOException;

public class WrappedPacketPlayOutRespawnImpl extends PacketPlayOutRespawn implements WrappedPacketPlayOutRespawn {

    public WrappedPacketPlayOutRespawnImpl(World world, Difficulty difficulty, EnumGameMode gameMode, WorldType worldType) {
        WrappedPacketDataSerializer serializer = NMSImpl.INSTANCE.getDataSerializer();
        serializer.serializeInt(((CraftWorld) world).getHandle().worldProvider.getDimension())
                .serializeByte(EnumDifficulty.valueOf(difficulty.name()).a())
                .serializeByte(WorldSettings.EnumGamemode.valueOf(gameMode.name()).getId());
        net.minecraft.server.v1_8_R3.WorldType type = net.minecraft.server.v1_8_R3.WorldType.getType(worldType.getName());
        serializer.serializeString(type == null ? net.minecraft.server.v1_8_R3.WorldType.NORMAL.name() : type.name());
        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WrappedPacketPlayOutRespawnImpl(World world, Difficulty difficulty, EnumGameMode gameMode) {
        this(world, difficulty, gameMode, WorldType.NORMAL);
    }
}
