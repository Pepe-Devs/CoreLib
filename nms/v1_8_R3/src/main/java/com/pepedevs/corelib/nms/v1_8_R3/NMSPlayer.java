package com.pepedevs.corelib.nms.v1_8_R3;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInAbilities;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class NMSPlayer implements com.pepedevs.corelib.nms.NMSPlayer {

    private final Player player;

    public NMSPlayer(Player player) {
        this.player = player;
    }


    @Override
    public Object getEntityPlayer() {
        return ((CraftPlayer) player).getHandle();
    }

    @Override
    public Object getPlayerConnection() {
        return ((CraftPlayer) player).getHandle().playerConnection;
    }

    @Override
    public Object getNetworkManager() {
        return ((CraftPlayer) player).getHandle().playerConnection.networkManager;
    }

    @Override
    public void sendPacket(Object packet) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket((Packet<?>) packet);
    }

    @Override
    public int getNextContainerCounter() {
        return ((CraftPlayer) player).getHandle().nextContainerCounter();
    }
}
