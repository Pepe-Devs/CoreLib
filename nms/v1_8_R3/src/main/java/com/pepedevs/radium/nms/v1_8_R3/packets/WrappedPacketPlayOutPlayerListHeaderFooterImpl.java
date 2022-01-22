package com.pepedevs.radium.nms.v1_8_R3.packets;

import com.pepedevs.radium.adventure.AdventureUtils;
import com.pepedevs.radium.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.radium.nms.packets.WrappedPacketPlayOutPlayerListHeaderFooter;
import com.pepedevs.radium.nms.v1_8_R3.NMSProviderImpl;
import net.kyori.adventure.text.Component;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;

import java.io.IOException;

public class WrappedPacketPlayOutPlayerListHeaderFooterImpl implements WrappedPacketPlayOutPlayerListHeaderFooter {

    private Component header;
    private Component footer;

    public WrappedPacketPlayOutPlayerListHeaderFooterImpl(String header, String footer) {
        this.header = AdventureUtils.fromVanillaString(header);
        this.footer = AdventureUtils.fromVanillaString(footer);
    }

    public WrappedPacketPlayOutPlayerListHeaderFooterImpl(Component header, Component footer) {
        this.header = header;
        this.footer = footer;
    }

    public WrappedPacketPlayOutPlayerListHeaderFooterImpl(Object header, Object footer) {
        this.header = AdventureUtils.asAdventure(header);
        this.footer = AdventureUtils.asAdventure(footer);
    }

    public WrappedPacketPlayOutPlayerListHeaderFooterImpl(WrappedPacketDataSerializer serializer) {
        PacketDataSerializer dataSerializer = (PacketDataSerializer) serializer;
        try {
            this.header = AdventureUtils.asAdventure(dataSerializer.d());
            this.footer = AdventureUtils.asAdventure(dataSerializer.d());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Component getHeader() {
        return header;
    }

    @Override
    public Component getFooter() {
        return footer;
    }

    @Override
    public void setHeader(Component header) {
        this.header = header;
    }

    @Override
    public void setFooter(Component footer) {
        this.footer = footer;
    }

    @Override
    public WrappedPacketDataSerializer buildData() {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        serializer.serializeComponent(header).serializeComponent(footer);
        return serializer;
    }

    @Override
    public Object buildPacket() {
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
        try {
            packet.a((PacketDataSerializer) buildData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packet;
    }
}
