package com.pepedevs.corelib.nms.v1_12_R1.packets;

import com.pepedevs.corelib.adventure.AdventureUtils;
import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutChat;
import com.pepedevs.corelib.nms.v1_12_R1.NMSProviderImpl;
import net.kyori.adventure.text.Component;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;

import java.io.IOException;

public class WrappedPacketPlayOutChatImpl implements WrappedPacketPlayOutChat {

    private Component message;
    private ChatMessageType messageType;

    public WrappedPacketPlayOutChatImpl(String component) {
        this.message = AdventureUtils.fromVanillaString(component);
        this.messageType = ChatMessageType.SYSTEM;
    }

    public WrappedPacketPlayOutChatImpl(Component component) {
        this.message = component;
        this.messageType = ChatMessageType.SYSTEM;
    }

    public WrappedPacketPlayOutChatImpl(String component, ChatMessageType type) {
        this.message = AdventureUtils.fromVanillaString(component);
        this.messageType = type;
    }

    public WrappedPacketPlayOutChatImpl(Component component, ChatMessageType type) {
        this.message = component;
        this.messageType = type;
    }

    public WrappedPacketPlayOutChatImpl(WrappedPacketDataSerializer serializer) {
        PacketDataSerializer dataSerializer = (PacketDataSerializer) serializer;
        this.message = AdventureUtils.asAdventure(dataSerializer.f());
        this.messageType = ChatMessageType.values()[dataSerializer.readByte()];
    }

    @Override
    public Component getMessage() {
        return message;
    }

    @Override
    public ChatMessageType getMessageType() {
        return messageType;
    }

    @Override
    public void setMessage(Component message) {
        this.message = message;
    }

    @Override
    public void setMessageType(ChatMessageType messageType) {
        this.messageType = messageType;
    }

    @Override
    public WrappedPacketDataSerializer buildData() {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        serializer.serializeComponent(this.message)
                .serializeByte(this.messageType.ordinal());
        return serializer;
    }

    @Override
    public Object buildPacket() {
        PacketPlayOutChat packet = new PacketPlayOutChat();
        try {
            packet.a((PacketDataSerializer) buildData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packet;
    }

}
