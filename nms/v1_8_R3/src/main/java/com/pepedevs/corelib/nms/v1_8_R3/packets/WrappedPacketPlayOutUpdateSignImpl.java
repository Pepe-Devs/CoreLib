package com.pepedevs.corelib.nms.v1_8_R3.packets;

import com.pepedevs.corelib.nms.packets.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.v1_8_R3.NMSImpl;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutUpdateSign;
import org.bukkit.Location;

import java.io.IOException;

public class WrappedPacketPlayOutUpdateSignImpl extends PacketPlayOutUpdateSign implements WrappedPacketPlayOutUpdateSign {

    public WrappedPacketPlayOutUpdateSignImpl(Location location, String... lines) {
        String[] newLines = new String[4];
        if (lines.length < 4) {
            System.arraycopy(lines, 0, newLines, 0, lines.length);
        }else {
            System.arraycopy(lines, 0 ,newLines, 0 ,newLines.length);
        }
        WrappedPacketDataSerializer serializer = NMSImpl.INSTANCE.getDataSerializer();
        serializer.serializeBlockPosition(location)
                .serializeComponent(newLines[0])
                .serializeComponent(newLines[1])
                .serializeComponent(newLines[2])
                .serializeComponent(newLines[3]);
    }

    public WrappedPacketPlayOutUpdateSignImpl(Location location, String first, String second, String third, String fourth) {
        WrappedPacketDataSerializer serializer = NMSImpl.INSTANCE.getDataSerializer();
        serializer.serializeBlockPosition(location)
                .serializeComponent(first)
                .serializeComponent(second)
                .serializeComponent(third)
                .serializeComponent(fourth);
        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
