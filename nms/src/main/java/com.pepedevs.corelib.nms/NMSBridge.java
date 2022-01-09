package com.pepedevs.corelib.nms;

import io.netty.buffer.ByteBuf;
import org.bukkit.entity.Player;

public interface NMSBridge {

    String craftChatMessageFromComponent(Object component);

    Object[] craftChatMessageFromString(String message);

    NMSPlayer getPlayer(Player player);

    WrappedPacketDataSerializer getDataSerializer();

    WrappedPacketDataSerializer getDataSerializer(ByteBuf byteBuf);

    void craftEventFactoryHandleInventoryClose(Player player);
}
