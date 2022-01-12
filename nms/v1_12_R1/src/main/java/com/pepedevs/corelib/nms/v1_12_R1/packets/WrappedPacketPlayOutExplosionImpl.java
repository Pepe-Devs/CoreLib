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
import java.util.*;

public class WrappedPacketPlayOutExplosionImpl implements WrappedPacketPlayOutExplosion {

    private Location location;
    private float power;
    private Set<Location> blocks;
    private Vector knockback;

    public WrappedPacketPlayOutExplosionImpl(Location location, float power, Set<Block> blocks, Vector knockback) {
        this.location = location;
        this.power = power;
        Set<Location> locations = new HashSet<>();
        for (Block block : blocks) {
            locations.add(block.getLocation());
        }
        this.blocks = locations;
        this.knockback = knockback;
    }

    public WrappedPacketPlayOutExplosionImpl(Location location, float power) {
        this(location, power, new HashSet<>(), new Vector(0,0,0));
    }

    public WrappedPacketPlayOutExplosionImpl(WrappedPacketDataSerializer serializer) {
        PacketDataSerializer dataSerializer = (PacketDataSerializer) serializer;
        this.location = new Location(null, dataSerializer.readFloat(), dataSerializer.readFloat(), dataSerializer.readFloat());
        this.power = dataSerializer.readFloat();
        this.blocks = new HashSet<>();

        int count = dataSerializer.readInt();

        int x = this.location.getBlockX();
        int y = this.location.getBlockY();
        int z = this.location.getBlockZ();

        for (int i = 0; i < count; i++) {
            this.blocks.add(new Location(null, dataSerializer.readByte() + x, dataSerializer.readByte() + y, dataSerializer.readByte() + z));
        }

        this.knockback = new Vector(dataSerializer.readFloat(), dataSerializer.readFloat(), dataSerializer.readFloat());
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public float getPower() {
        return power;
    }

    @Override
    public Set<Location> getBlocks() {
        return blocks;
    }

    @Override
    public Vector getKnockback() {
        return knockback;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public void setPower(float power) {
        this.power = power;
    }

    @Override
    public void setBlocks(Set<Location> blocks) {
        this.blocks = blocks;
    }

    @Override
    public void setKnockback(Vector knockback) {
        this.knockback = knockback;
    }

    @Override
    public WrappedPacketDataSerializer buildData() {
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

        Iterator<Location> blockIterator = blocks.iterator();
        while (blockIterator.hasNext()) {
            BlockPosition position = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
            int subX = position.getX() - x;
            int subY = position.getY() - y;
            int subZ = position.getZ() - z;
            serializer.serializeByte(subX).serializeByte(subY).serializeByte(subZ);
        }
        serializer
                .serializeFloat((float) knockback.getX())
                .serializeFloat((float) knockback.getY())
                .serializeFloat((float) knockback.getZ());
        return serializer;
    }

    @Override
    public Object buildPacket() {
        PacketPlayOutExplosion packet = new PacketPlayOutExplosion();
        try {
            packet.a((PacketDataSerializer) buildData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packet;
    }

}
