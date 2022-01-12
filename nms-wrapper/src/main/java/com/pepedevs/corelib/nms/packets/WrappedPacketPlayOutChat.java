package com.pepedevs.corelib.nms.packets;

import net.kyori.adventure.text.Component;

public interface WrappedPacketPlayOutChat extends WrappedPacket {

    Component getMessage();

    ChatMessageType getMessageType();

    void setMessage(Component message);

    void setMessageType(ChatMessageType messageType);

    enum ChatMessageType {
        CHAT,
        SYSTEM,
        ACTION_BAR;
    }

}
