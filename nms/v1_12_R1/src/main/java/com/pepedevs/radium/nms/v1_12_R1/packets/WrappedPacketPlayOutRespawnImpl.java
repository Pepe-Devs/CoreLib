package com.pepedevs.radium.nms.v1_12_R1.packets;

import com.pepedevs.radium.nms.EnumGameMode;
import com.pepedevs.radium.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.radium.nms.packets.WrappedPacketPlayOutRespawn;
import com.pepedevs.radium.nms.v1_12_R1.NMSProviderImpl;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;

import java.io.IOException;

public class WrappedPacketPlayOutRespawnImpl extends PacketPlayOutRespawn implements WrappedPacketPlayOutRespawn {

    private int dimension;
    private Difficulty difficulty;
    private EnumGameMode gameMode;
    private WorldType worldType;

    public WrappedPacketPlayOutRespawnImpl(World world, Difficulty difficulty, EnumGameMode gameMode, WorldType worldType) {
        this.dimension = ((CraftWorld) world).getHandle().worldProvider.getDimensionManager().getDimensionID();
        this.difficulty = difficulty;
        this.gameMode = gameMode;
        this.worldType = worldType;
    }

    public WrappedPacketPlayOutRespawnImpl(World world, Difficulty difficulty, EnumGameMode gameMode) {
        this(world, difficulty, gameMode, WorldType.NORMAL);
    }

    public WrappedPacketPlayOutRespawnImpl(WrappedPacketDataSerializer serializer) {
        PacketDataSerializer dataSerializer = (PacketDataSerializer) serializer;
        this.dimension = dataSerializer.readInt();
        this.difficulty = Difficulty.valueOf(EnumDifficulty.getById(dataSerializer.readUnsignedByte()).name());
        this.gameMode = EnumGameMode.valueOf(EnumGamemode.getById(dataSerializer.readUnsignedByte()).name());
        this.worldType = WorldType.getByName(net.minecraft.server.v1_12_R1.WorldType.getType(dataSerializer.e(16)).name());
    }

    @Override
    public int getDimension() {
        return dimension;
    }

    @Override
    public Difficulty getDifficulty() {
        return difficulty;
    }

    @Override
    public EnumGameMode getGameMode() {
        return gameMode;
    }

    @Override
    public WorldType getWorldType() {
        return worldType;
    }

    @Override
    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    @Override
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public void setGameMode(EnumGameMode gameMode) {
        this.gameMode = gameMode;
    }

    @Override
    public void setWorldType(WorldType worldType) {
        this.worldType = worldType;
    }

    @Override
    public WrappedPacketDataSerializer buildData() {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        serializer.serializeInt(dimension)
                .serializeByte(EnumDifficulty.valueOf(this.difficulty.name()).a())
                .serializeByte(EnumGamemode.valueOf(this.gameMode.name()).getId());
        net.minecraft.server.v1_12_R1.WorldType type = net.minecraft.server.v1_12_R1.WorldType.getType(this.worldType.getName());
        serializer.serializeString(type == null ? net.minecraft.server.v1_12_R1.WorldType.NORMAL.name() : type.name());
        return serializer;
    }

    @Override
    public Object buildPacket() {
        PacketPlayOutRespawn packet = new PacketPlayOutRespawn();
        try {
            packet.a((PacketDataSerializer) buildData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packet;
    }
}
