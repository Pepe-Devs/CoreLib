package com.pepedevs.corelib.nms.v1_8_R3.packets;

import com.pepedevs.corelib.nms.packets.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutSpawnEntityLiving;
import com.pepedevs.corelib.nms.v1_8_R3.NMSImpl;
import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import java.io.IOException;

public class WrappedPacketPlayOutSpawnEntityLivingImpl extends PacketPlayOutSpawnEntityLiving implements WrappedPacketPlayOutSpawnEntityLiving {

    public WrappedPacketPlayOutSpawnEntityLivingImpl(int entityId, EntityType entity, Location location, int headPitch, Vector velocity, Object dataWatcher) {
        WrappedPacketDataSerializer serializer = NMSImpl.INSTANCE.getDataSerializer();
        serializer.serializeIntToByte(entityId)
                .serializeByte(((byte) entity.getTypeId()) & 255)
                .serializeInt(MathHelper.floor(location.getX() * 32.0D))
                .serializeInt(MathHelper.floor(location.getY() * 32.0D))
                .serializeInt(MathHelper.floor(location.getZ() * 32.0D))
                .serializeByte((byte) ((int) (location.getYaw() * 256.0F / 360.0F)))
                .serializeByte((byte) ((int) (location.getPitch() * 256.0F / 360.0F)))
                .serializeByte((byte) ((int) (headPitch * 256.0F / 360.0F)))
                .serializeShort((int) (velocity.getX() * 8000.0D))
                .serializeShort((int) (velocity.getY() * 8000.0D))
                .serializeShort((int) (velocity.getZ() * 8000.0D));

        try {
            ((DataWatcher) dataWatcher).a((PacketDataSerializer) serializer);
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WrappedPacketPlayOutSpawnEntityLivingImpl(int entityId, EntityType entity, Location location, int headPitch, Object dataWatcher) {
        this(entityId, entity, location, headPitch, new Vector(0, 0, 0), dataWatcher);
    }

    public WrappedPacketPlayOutSpawnEntityLivingImpl(WrappedPacketDataSerializer serializer) {
        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
