package com.pepedevs.corelib.nms.v1_12_R1.packets;

import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutOpenSignEditor;
import com.pepedevs.corelib.nms.v1_12_R1.NMSProviderImpl;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutOpenSignEditor;
import org.bukkit.Location;

import java.io.IOException;

public class WrappedPacketPlayOutOpenSignEditorImpl extends PacketPlayOutOpenSignEditor implements WrappedPacketPlayOutOpenSignEditor {

    private Location location;

    public WrappedPacketPlayOutOpenSignEditorImpl(Location location) {
        this.location = location;
    }

    public WrappedPacketPlayOutOpenSignEditorImpl(Object blockPos) {
        BlockPosition blockPosition = ((BlockPosition) blockPos);
        this.location = new Location(null, blockPosition.getX(), blockPosition.getY(), blockPosition.getZ());
    }

    public WrappedPacketPlayOutOpenSignEditorImpl(WrappedPacketDataSerializer serializer) {
        BlockPosition position = ((PacketDataSerializer) serializer).e();
        this.location = new Location(null, position.getX(), position.getY(), position.getZ());
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public WrappedPacketDataSerializer buildData() {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        serializer.serializeBlockPosition(location);
        return serializer;
    }

    @Override
    public Object buildPacket() {
        PacketPlayOutOpenSignEditor packet = new PacketPlayOutOpenSignEditor();
        try {
            packet.a((PacketDataSerializer) buildData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packet;
    }

}
