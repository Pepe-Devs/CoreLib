package com.pepedevs.corelib.nms.v1_8_R3;

import com.mojang.authlib.GameProfile;
import com.pepedevs.corelib.nms.EnumGameMode;
import com.pepedevs.corelib.nms.NMSProvider;
import com.pepedevs.corelib.nms.NMSPlayer;
import com.pepedevs.corelib.nms.packets.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutPlayerInfo;
import com.pepedevs.corelib.nms.v1_8_R3.packets.WrappedPacketPlayOutPlayerInfoImpl;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.kyori.adventure.text.Component;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;

public class NMSImpl implements NMSProvider {

    public static final NMSImpl INSTANCE = new NMSImpl();

    private NMSImpl(){}

    @Override
    public String craftChatMessageFromComponent(Object component) {
        IChatBaseComponent chatBaseComponent = (IChatBaseComponent) component;
        return CraftChatMessage.fromComponent(chatBaseComponent);
    }

    @Override
    public Object[] craftChatMessageFromString(String message) {
        return CraftChatMessage.fromString(message);
    }

    @Override
    public NMSPlayer getPlayer(Player player) {
        return new com.pepedevs.corelib.nms.v1_8_R3.NMSPlayer(player);
    }

    @Override
    public WrappedPacketDataSerializer getDataSerializer() {
        return this.getDataSerializer(Unpooled.buffer());
    }

    @Override
    public WrappedPacketDataSerializer getDataSerializer(ByteBuf byteBuf) {
        return new WrappedPacketDataSerializerImpl(byteBuf);
    }

    @Override
    public WrappedPacketPlayOutPlayerInfo.WrappedPlayerInfoData getPlayerInfo(GameProfile gameProfile, int latency, EnumGameMode gamemode, String name) {
        WrappedPacketPlayOutPlayerInfoImpl uselessShit = new WrappedPacketPlayOutPlayerInfoImpl();
        return uselessShit.new WrappedPlayerInfoDataImpl(gameProfile, latency, gamemode, name);
    }

    @Override
    public WrappedPacketPlayOutPlayerInfo.WrappedPlayerInfoData getPlayerInfo(GameProfile gameProfile, int latency, EnumGameMode gamemode, Component name) {
        WrappedPacketPlayOutPlayerInfoImpl uselessShit = new WrappedPacketPlayOutPlayerInfoImpl();
        return uselessShit.new WrappedPlayerInfoDataImpl(gameProfile, latency, gamemode, name);
    }

    @Override
    public WrappedPacketPlayOutPlayerInfo.WrappedPlayerInfoData getPlayerInfo(GameProfile gameProfile, int latency, EnumGameMode gamemode, Object name) {
        WrappedPacketPlayOutPlayerInfoImpl uselessShit = new WrappedPacketPlayOutPlayerInfoImpl();
        return uselessShit.new WrappedPlayerInfoDataImpl(gameProfile, latency, gamemode, name);
    }

    @Override
    public void craftEventFactoryHandleInventoryClose(Player player) {
        CraftEventFactory.handleInventoryCloseEvent(((CraftPlayer) player).getHandle());
    }
}
