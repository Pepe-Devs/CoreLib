package com.pepedevs.corelib.nms.v1_8_R3.packets;

import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutUpdateSign;
import com.pepedevs.corelib.nms.v1_8_R3.NMSProviderImpl;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutUpdateSign;
import org.bukkit.Location;

import java.io.IOException;

public class WrappedPacketPlayOutUpdateSignImpl extends PacketPlayOutUpdateSign implements WrappedPacketPlayOutUpdateSign {

    private Location location;
    private String[] lines;

    public WrappedPacketPlayOutUpdateSignImpl(Location location, String... lines) {
        this.location = location;
        String[] newLines = new String[4];
        if (lines.length < 4) {
            System.arraycopy(lines, 0, newLines, 0, lines.length);
        }else {
            System.arraycopy(lines, 0 ,newLines, 0 ,newLines.length);
        }
        this.lines = newLines;

    }

    public WrappedPacketPlayOutUpdateSignImpl(Location location, String first, String second, String third, String fourth) {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
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

    public WrappedPacketPlayOutUpdateSignImpl(WrappedPacketDataSerializer serializer) {
        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public String[] getLines() {
        return lines;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public void setLines(String[] lines) {
        this.lines = lines;
    }

    @Override
    public WrappedPacketDataSerializer buildData() {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        serializer.serializeBlockPosition(location)
                .serializeComponent(this.lines[0])
                .serializeComponent(this.lines[1])
                .serializeComponent(this.lines[2])
                .serializeComponent(this.lines[3]);
        return serializer;
    }

    @Override
    public Object buildPacket() {
        PacketPlayOutUpdateSign packet = new PacketPlayOutUpdateSign();
        try {
            packet.a((PacketDataSerializer) buildData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packet;
    }
}
