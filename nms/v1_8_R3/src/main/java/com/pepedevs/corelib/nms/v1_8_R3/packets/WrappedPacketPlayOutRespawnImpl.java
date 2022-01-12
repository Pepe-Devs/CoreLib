package com.pepedevs.corelib.nms.v1_8_R3.packets;

import com.pepedevs.corelib.nms.EnumGameMode;
import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutRespawn;
import com.pepedevs.corelib.nms.v1_8_R3.NMSProviderImpl;
import net.minecraft.server.v1_8_R3.EnumDifficulty;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutRespawn;
import net.minecraft.server.v1_8_R3.WorldSettings;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

import java.io.IOException;

public class WrappedPacketPlayOutRespawnImpl implements WrappedPacketPlayOutRespawn {

    private int dimension;
    private Difficulty difficulty;
    private EnumGameMode gameMode;
    private WorldType worldType;

    public WrappedPacketPlayOutRespawnImpl(World world, Difficulty difficulty, EnumGameMode gameMode, WorldType worldType) {
        this.dimension = ((CraftWorld) world).getHandle().worldProvider.getDimension();
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
        this.gameMode = EnumGameMode.valueOf(WorldSettings.EnumGamemode.getById(dataSerializer.readUnsignedByte()).name());
        this.worldType = WorldType.getByName(net.minecraft.server.v1_8_R3.WorldType.getType(dataSerializer.c(16)).name());
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
                .serializeByte(WorldSettings.EnumGamemode.valueOf(this.gameMode.name()).getId());
        net.minecraft.server.v1_8_R3.WorldType type = net.minecraft.server.v1_8_R3.WorldType.getType(this.worldType.getName());
        serializer.serializeString(type == null ? net.minecraft.server.v1_8_R3.WorldType.NORMAL.name() : type.name());
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
