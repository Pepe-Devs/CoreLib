package com.pepedevs.corelib.nms.v1_12_R1.packets;

import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutExplosion;
import com.pepedevs.corelib.nms.v1_12_R1.NMSProviderImpl;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutExplosion;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class WrappedPacketPlayOutExplosionImpl extends PacketPlayOutExplosion implements WrappedPacketPlayOutExplosion {

    public WrappedPacketPlayOutExplosionImpl(Location location, float power, List<Location> blocks, Vector knockback) {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        serializer
                .serializeFloat((float) location.getX())
                .serializeFloat((float) location.getY())
                .serializeFloat((float) location.getZ())
                .serializeFloat(power)
                .serializeInt(blocks.size());
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        Iterator<Location> locationIterator = blocks.iterator();
        while (locationIterator.hasNext()) {
            BlockPosition position = new BlockPosition(location.getX(), location.getY(), location.getZ());
            int subX = position.getX() - x;
            int subY = position.getY() - y;
            int subZ = position.getZ() - z;
            serializer.serializeByte(subX).serializeByte(subY).serializeByte(subZ);
        }
        serializer
                .serializeFloat((float) knockback.getX())
                .serializeFloat((float) knockback.getY())
                .serializeFloat((float) knockback.getZ());
        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WrappedPacketPlayOutExplosionImpl(Location location, float power, Set<Block> blocks, Vector knockback) {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        serializer
                .serializeFloat((float) location.getX())
                .serializeFloat((float) location.getY())
                .serializeFloat((float) location.getZ())
                .serializeFloat(power)
                .serializeInt(blocks.size());
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        Iterator<Block> blockIterator = blocks.iterator();
        while (blockIterator.hasNext()) {
            BlockPosition position = new BlockPosition(location.getX(), location.getY(), location.getZ());
            int subX = position.getX() - x;
            int subY = position.getY() - y;
            int subZ = position.getZ() - z;
            serializer.serializeByte(subX).serializeByte(subY).serializeByte(subZ);
        }
        serializer
                .serializeFloat((float) knockback.getX())
                .serializeFloat((float) knockback.getY())
                .serializeFloat((float) knockback.getZ());
        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WrappedPacketPlayOutExplosionImpl(Location location, float power) {
        this(location, power, new ArrayList<>(), new Vector(0,0,0));
    }

    public WrappedPacketPlayOutExplosionImpl(WrappedPacketDataSerializer serializer) {
        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
