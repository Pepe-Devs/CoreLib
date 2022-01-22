package com.pepedevs.radium.nms.v1_8_R3.packets;

import com.pepedevs.radium.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.radium.nms.packets.WrappedPacketPlayOutCloseWindow;
import com.pepedevs.radium.nms.v1_8_R3.NMSProviderImpl;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutCloseWindow;

import java.io.IOException;

public class WrappedPacketPlayOutCloseWindowImpl implements WrappedPacketPlayOutCloseWindow {

    private int containerID;

    public WrappedPacketPlayOutCloseWindowImpl(int containerID) {
        this.containerID = containerID;
    }

    public WrappedPacketPlayOutCloseWindowImpl(WrappedPacketDataSerializer serializer) {
        this.containerID = ((PacketDataSerializer) serializer).readByte();
    }

    @Override
    public int getContainerID() {
        return containerID;
    }

    @Override
    public void setContainerID(int containerID) {
        this.containerID = containerID;
    }

    @Override
    public WrappedPacketDataSerializer buildData() {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        serializer.serializeByte(this.containerID);
        return serializer;
    }

    @Override
    public Object buildPacket() {
        PacketPlayOutCloseWindow packet = new PacketPlayOutCloseWindow();
        try {
            packet.a((PacketDataSerializer) buildData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packet;
    }
}
