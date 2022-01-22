package com.pepedevs.radium.nms.v1_8_R3.packets;

import com.pepedevs.radium.adventure.AdventureUtils;
import com.pepedevs.radium.nms.InventoryType;
import com.pepedevs.radium.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.radium.nms.packets.WrappedPacketPlayOutOpenWindow;
import com.pepedevs.radium.nms.v1_8_R3.NMSProviderImpl;
import net.kyori.adventure.text.Component;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutOpenWindow;

import java.io.IOException;

public class WrappedPacketPlayOutOpenWindowImpl implements WrappedPacketPlayOutOpenWindow {

    private int nextContainerCounter;
    private Component title;
    private InventoryType type;
    private int size;

    public WrappedPacketPlayOutOpenWindowImpl(int nextContainerCounter, String title, InventoryType type) {
        this(nextContainerCounter, AdventureUtils.fromVanillaString(title), type);
    }

    public WrappedPacketPlayOutOpenWindowImpl(int nextContainerCounter, Component title, InventoryType type) {
        this.nextContainerCounter = nextContainerCounter;
        this.title = title;
        this.type = type;
        this.size = type.SIZE;
    }

    public WrappedPacketPlayOutOpenWindowImpl(int nextContainerCounter, String title, InventoryType type, int rows) {
        this(nextContainerCounter, AdventureUtils.fromVanillaString(title), type, rows);
    }

    public WrappedPacketPlayOutOpenWindowImpl(int nextContainerCounter, Component title, InventoryType type, int rows) {
        this.nextContainerCounter = nextContainerCounter;
        this.title = title;
        this.type = type;
        this.size = rows * 9;
    }

    public WrappedPacketPlayOutOpenWindowImpl(WrappedPacketDataSerializer serializer) {
        PacketDataSerializer dataSerializer = (PacketDataSerializer) serializer;
        this.nextContainerCounter = dataSerializer.readUnsignedByte();
        this.type = InventoryType.valueOf(dataSerializer.c(32).replace("minecraft:", ""));
        try {
            this.title = AdventureUtils.asAdventure(dataSerializer.d());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.size = dataSerializer.readUnsignedByte();
    }

    @Override
    public int getNextContainerCounter() {
        return nextContainerCounter;
    }

    @Override
    public Component getTitle() {
        return title;
    }

    @Override
    public InventoryType getType() {
        return type;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setNextContainerCounter(int nextContainerCounter) {
        this.nextContainerCounter = nextContainerCounter;
    }

    @Override
    public void setTitle(Component title) {
        this.title = title;
    }

    @Override
    public void setType(InventoryType type) {
        this.type = type;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public WrappedPacketDataSerializer buildData() {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        serializer.serializeByte(this.nextContainerCounter);
        serializer.serializeString("minecraft:" +  this.type.name().toLowerCase());
        serializer.serializeComponent(this.title);
        serializer.serializeByte(this.size);
        return serializer;
    }

    @Override
    public Object buildPacket() {
        PacketPlayOutOpenWindow packet = new PacketPlayOutOpenWindow();
        try {
            packet.a((PacketDataSerializer) buildData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packet;
    }

}
