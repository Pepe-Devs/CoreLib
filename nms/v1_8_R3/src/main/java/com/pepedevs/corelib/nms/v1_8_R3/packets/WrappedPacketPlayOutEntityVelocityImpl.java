package com.pepedevs.corelib.nms.v1_8_R3.packets;

import com.pepedevs.corelib.nms.packets.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutEntityVelocity;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityVelocity;
import org.bukkit.util.Vector;

import java.io.IOException;

public class WrappedPacketPlayOutEntityVelocityImpl extends PacketPlayOutEntityVelocity implements WrappedPacketPlayOutEntityVelocity {

    public WrappedPacketPlayOutEntityVelocityImpl(int entityID, Vector vector) {
        super(entityID, vector.getX(), vector.getY(), vector.getZ());
    }

    public WrappedPacketPlayOutEntityVelocityImpl(WrappedPacketDataSerializer serializer) {
        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
