package com.pepedevs.corelib.nms.v1_12_R1.packets;

import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutOpenSignEditor;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutOpenSignEditor;
import org.bukkit.Location;

import java.io.IOException;

public class WrappedPacketPlayOutOpenSignEditorImpl extends PacketPlayOutOpenSignEditor implements WrappedPacketPlayOutOpenSignEditor {

    public WrappedPacketPlayOutOpenSignEditorImpl(Location location) {
        super(new BlockPosition(location.getX(), location.getY(), location.getZ()));
    }

    public WrappedPacketPlayOutOpenSignEditorImpl(Object blockPos) {
        super((BlockPosition) blockPos);
    }

    public WrappedPacketPlayOutOpenSignEditorImpl(WrappedPacketDataSerializer serializer) {
        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
