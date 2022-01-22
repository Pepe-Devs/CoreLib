package com.pepedevs.radium.nms.v1_12_R1.packets;

import com.pepedevs.radium.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.radium.nms.packets.WrappedPacketPlayOutExplosion;
import com.pepedevs.radium.nms.v1_12_R1.NMSProviderImpl;
import net.minecraft.server.v1_12_R1.MathHelper;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutExplosion;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WrappedPacketPlayOutExplosionImpl implements WrappedPacketPlayOutExplosion {

    private Vector location;
    private float power;
    private Set<Vector> blocks;
    private Vector knockback;

    public WrappedPacketPlayOutExplosionImpl(Vector location, float power, Set<Block> blocks, Vector knockback) {
        this.location = location;
        this.power = power;
        Set<Vector> locations = new HashSet<>();
        for (Block block : blocks) {
            locations.add(block.getLocation().toVector());
        }
        this.blocks = locations;
        this.knockback = knockback;
    }

    public WrappedPacketPlayOutExplosionImpl(Vector location, float power, List<Vector> blocks, Vector knockback) {
        this.location = location;
        this.power = power;
        this.blocks = new HashSet<>(blocks);
        this.knockback = knockback;
    }

    public WrappedPacketPlayOutExplosionImpl(Vector location, float power) {
        this(location, power, new ArrayList<>(), new Vector(0,0,0));
    }

    public WrappedPacketPlayOutExplosionImpl(WrappedPacketDataSerializer serializer) {
        PacketDataSerializer dataSerializer = (PacketDataSerializer) serializer;
        this.location = new Vector(dataSerializer.readFloat(), dataSerializer.readFloat(), dataSerializer.readFloat());
        this.power = dataSerializer.readFloat();
        this.blocks = new HashSet<>();

        int count = dataSerializer.readInt();

        int x = this.location.getBlockX();
        int y = this.location.getBlockY();
        int z = this.location.getBlockZ();

        for (int i = 0; i < count; i++) {
            this.blocks.add(new Vector(dataSerializer.readByte() + x, dataSerializer.readByte() + y, dataSerializer.readByte() + z));
        }

        this.knockback = new Vector(dataSerializer.readFloat(), dataSerializer.readFloat(), dataSerializer.readFloat());
    }

    @Override
    public Vector getLocation() {
        return location;
    }

    @Override
    public float getPower() {
        return power;
    }

    @Override
    public Set<Vector> getBlocks() {
        return blocks;
    }

    @Override
    public Vector getKnockback() {
        return knockback;
    }

    @Override
    public void setLocation(Vector location) {
        this.location = location;
    }

    @Override
    public void setPower(float power) {
        this.power = power;
    }

    @Override
    public void setBlocks(Set<Vector> blocks) {
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
                .serializeFloat((float) this.location.getX())
                .serializeFloat((float) this.location.getY())
                .serializeFloat((float) this.location.getZ())
                .serializeFloat(power)
                .serializeInt(blocks.size());
        int x = this.location.getBlockX();
        int y = this.location.getBlockY();
        int z = this.location.getBlockZ();

        for (Vector block : this.blocks) {
            int subX = MathHelper.floor(block.getX()) - x;
            int subY = MathHelper.floor(block.getY()) - y;
            int subZ = MathHelper.floor(block.getZ()) - z;
            serializer.serializeByte(subX)
                    .serializeByte(subY)
                    .serializeByte(subZ);
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
