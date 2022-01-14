package com.pepedevs.corelib.nms.packets;

public interface WrappedPacketPlayOutAnimation extends WrappedPacket {

    int getEntityId();

    void setEntityId(int entityId);

    AnimationType getAnimationType();

    void setAnimationType(AnimationType animationType);

    enum AnimationType {
        SWING_MAINHAND,
        TAKE_DAMAGE,
        SWING_OFFHAND,
        CRITICAL_DAMAGE,
        MAGICAL_DAMAGE;
    }

}
