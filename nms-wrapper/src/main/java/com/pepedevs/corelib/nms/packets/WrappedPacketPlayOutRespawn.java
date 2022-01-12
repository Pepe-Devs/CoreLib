package com.pepedevs.corelib.nms.packets;

import com.pepedevs.corelib.nms.EnumGameMode;
import org.bukkit.Difficulty;
import org.bukkit.WorldType;

public interface WrappedPacketPlayOutRespawn extends WrappedPacket {
    int getDimension();

    Difficulty getDifficulty();

    EnumGameMode getGameMode();

    WorldType getWorldType();

    void setDimension(int dimension);

    void setDifficulty(Difficulty difficulty);

    void setGameMode(EnumGameMode gameMode);

    void setWorldType(WorldType worldType);
}
