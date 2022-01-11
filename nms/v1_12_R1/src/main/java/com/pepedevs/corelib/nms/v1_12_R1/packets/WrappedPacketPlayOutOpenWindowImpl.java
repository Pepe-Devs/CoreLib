package com.pepedevs.corelib.nms.v1_12_R1.packets;

import com.pepedevs.corelib.nms.InventoryType;
import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutOpenWindow;
import com.pepedevs.corelib.nms.v1_12_R1.NMSProviderImpl;
import net.kyori.adventure.text.Component;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutOpenWindow;

import java.io.IOException;

public class WrappedPacketPlayOutOpenWindowImpl extends PacketPlayOutOpenWindow implements WrappedPacketPlayOutOpenWindow {

    public WrappedPacketPlayOutOpenWindowImpl(int nextContainerCounter, String title, InventoryType type) {
        try {
            this.a(getSerializer(nextContainerCounter, title, type, type.SIZE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WrappedPacketPlayOutOpenWindowImpl(int nextContainerCounter, Component title, InventoryType type) {
        try {
            this.a(getSerializer(nextContainerCounter, title, type, type.SIZE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WrappedPacketPlayOutOpenWindowImpl(int nextContainerCounter, String title, InventoryType type, int rows) {
        try {
            this.a(getSerializer(nextContainerCounter, title, type, (byte) (rows * 9)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WrappedPacketPlayOutOpenWindowImpl(int nextContainerCounter, Component title, InventoryType type, int rows) {
        try {
            this.a(getSerializer(nextContainerCounter, title, type, (byte) (rows * 9)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private PacketDataSerializer getSerializer(int nextContainerCounter, String title, InventoryType type, byte size) {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        serializer.serializeByte(nextContainerCounter);
        serializer.serializeString("minecraft:" +  type.name().toLowerCase());
        serializer.serializeComponent(title);
        serializer.serializeByte(size);
        return (PacketDataSerializer) serializer;
    }

    private PacketDataSerializer getSerializer(int nextContainerCounter, Component title, InventoryType type, byte size) {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        serializer.serializeByte(nextContainerCounter);
        serializer.serializeString("minecraft:" +  type.name().toLowerCase());
        serializer.serializeComponent(title);
        serializer.serializeByte(size);
        return (PacketDataSerializer) serializer;
    }

    public WrappedPacketPlayOutOpenWindowImpl(WrappedPacketDataSerializer serializer) {
        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
