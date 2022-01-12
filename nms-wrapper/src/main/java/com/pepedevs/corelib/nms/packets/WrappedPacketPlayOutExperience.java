package com.pepedevs.corelib.nms.packets;

public interface WrappedPacketPlayOutExperience extends WrappedPacket {
    float getXp();

    int getTotalXp();

    int getLevel();

    void setXp(float xp);

    void setTotalXp(int totalXp);

    void setLevel(int level);
}
