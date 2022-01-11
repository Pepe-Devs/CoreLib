package com.pepedevs.corelib.nms.v1_8_R3.packets;

import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutPlayerListHeaderFooter;
import com.pepedevs.corelib.nms.v1_8_R3.NMSProviderImpl;
import net.kyori.adventure.text.Component;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;

import java.io.IOException;

public class WrappedPacketPlayOutPlayerListHeaderFooterImpl extends PacketPlayOutPlayerListHeaderFooter implements WrappedPacketPlayOutPlayerListHeaderFooter {

    public WrappedPacketPlayOutPlayerListHeaderFooterImpl(String header, String footer) {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        serializer.serializeComponent(header).serializeComponent(footer);
        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WrappedPacketPlayOutPlayerListHeaderFooterImpl(Component header, Component footer) {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        serializer.serializeComponent(header).serializeComponent(footer);
        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WrappedPacketPlayOutPlayerListHeaderFooterImpl(Object header, Object footer) {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        serializer.serializeComponent(header).serializeComponent(footer);
        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WrappedPacketPlayOutPlayerListHeaderFooterImpl(WrappedPacketDataSerializer serializer) {
        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
