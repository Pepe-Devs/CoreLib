package com.pepedevs.corelib.nms.packets;

public interface WrappedPacketPlayOutChat extends WrappedPacket {

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
