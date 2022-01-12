package com.pepedevs.corelib.nms.packets;

import net.kyori.adventure.text.Component;

public interface WrappedPacketPlayOutChat extends WrappedPacket {

    Component getMessage();

    ChatMessageType getMessageType();

    void setMessage(Component message);

    void setMessageType(ChatMessageType messageType);

    enum ChatMessageType {
        CHAT(0),
        SYSTEM(1),
        ACTION_BAR(2);

        ChatMessageType(int a) {
            this.BYTE = (byte) a;
        }

        public final byte BYTE;
    }

}
