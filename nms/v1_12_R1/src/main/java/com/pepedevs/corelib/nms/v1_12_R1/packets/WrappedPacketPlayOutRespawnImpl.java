package com.pepedevs.corelib.nms.v1_12_R1.packets;

import com.pepedevs.corelib.nms.EnumGameMode;
import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutRespawn;
import com.pepedevs.corelib.nms.v1_12_R1.NMSProviderImpl;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;

import java.io.IOException;

public class WrappedPacketPlayOutRespawnImpl extends PacketPlayOutRespawn implements WrappedPacketPlayOutRespawn {

    public WrappedPacketPlayOutRespawnImpl(World world, Difficulty difficulty, EnumGameMode gameMode, WorldType worldType) {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        serializer.serializeInt(((CraftWorld) world).getHandle().worldProvider.getDimensionManager().getDimensionID())
                .serializeByte(EnumDifficulty.valueOf(difficulty.name()).a())
                .serializeByte(EnumGamemode.valueOf(gameMode.name()).getId());
        net.minecraft.server.v1_12_R1.WorldType type = net.minecraft.server.v1_12_R1.WorldType.getType(worldType.getName());
        serializer.serializeString(type == null ? net.minecraft.server.v1_12_R1.WorldType.NORMAL.name() : type.name());
        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WrappedPacketPlayOutRespawnImpl(World world, Difficulty difficulty, EnumGameMode gameMode) {
        this(world, difficulty, gameMode, WorldType.NORMAL);
    }

    public WrappedPacketPlayOutRespawnImpl(WrappedPacketDataSerializer serializer) {
        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
