package com.pepedevs.corelib.nms.v1_12_R1.packets;

import com.pepedevs.corelib.adventure.AdventureUtils;
import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutPlayerListHeaderFooter;
import com.pepedevs.corelib.nms.v1_12_R1.NMSProviderImpl;
import net.kyori.adventure.text.Component;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerListHeaderFooter;

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
        this.header = AdventureUtils.asAdventure(dataSerializer.f());
        this.footer = AdventureUtils.asAdventure(dataSerializer.f());
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
