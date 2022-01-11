package com.pepedevs.corelib.nms.v1_12_R1.packets;

import com.pepedevs.corelib.adventure.AdventureUtils;
import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutChat;
import com.pepedevs.corelib.nms.v1_12_R1.NMSProviderImpl;
import net.kyori.adventure.text.Component;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;

import java.io.IOException;

public class WrappedPacketPlayOutChatImpl extends PacketPlayOutChat implements WrappedPacketPlayOutChat {

    public WrappedPacketPlayOutChatImpl(String component) {
        super((IChatBaseComponent) NMSProviderImpl.INSTANCE.craftChatMessageFromString(component)[0]);
    }

    public WrappedPacketPlayOutChatImpl(Component component) {
        super((IChatBaseComponent) AdventureUtils.asVanilla(component));
    }

    public WrappedPacketPlayOutChatImpl(String component, ChatMessageType type) {
        super((IChatBaseComponent) NMSProviderImpl.INSTANCE.craftChatMessageFromString(component)[0], net.minecraft.server.v1_12_R1.ChatMessageType.a(type.BYTE));
    }

    public WrappedPacketPlayOutChatImpl(Component component, ChatMessageType type) {
        super((IChatBaseComponent) AdventureUtils.asVanilla(component), net.minecraft.server.v1_12_R1.ChatMessageType.a(type.BYTE));
    }

    public WrappedPacketPlayOutChatImpl(WrappedPacketDataSerializer serializer) {
        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
