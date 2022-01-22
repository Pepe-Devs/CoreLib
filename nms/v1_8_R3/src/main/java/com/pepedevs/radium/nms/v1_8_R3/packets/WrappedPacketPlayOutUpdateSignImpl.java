package com.pepedevs.radium.nms.v1_8_R3.packets;

import com.pepedevs.radium.adventure.AdventureUtils;
import com.pepedevs.radium.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.radium.nms.packets.WrappedPacketPlayOutUpdateSign;
import com.pepedevs.radium.nms.v1_8_R3.NMSProviderImpl;
import net.kyori.adventure.text.Component;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutUpdateSign;
import org.bukkit.util.Vector;

import java.io.IOException;

public class WrappedPacketPlayOutUpdateSignImpl implements WrappedPacketPlayOutUpdateSign {

    private Vector location;
    private Component[] lines;

    public WrappedPacketPlayOutUpdateSignImpl(Vector location, String... lines) {
        this(location, AdventureUtils.fromVanillaString(lines));
    }

    public WrappedPacketPlayOutUpdateSignImpl(Vector location, Component... lines) {
        this.location = location;
        Component[] newLines = new Component[4];
        if (lines.length < 4) {
            System.arraycopy(lines, 0, newLines, 0, lines.length);
        }else {
            System.arraycopy(lines, 0 ,newLines, 0 ,newLines.length);
        }
        this.lines = newLines;
    }

    public WrappedPacketPlayOutUpdateSignImpl(Vector location, Object... lines) {
        this(location, AdventureUtils.asAdventure(lines));
    }

    public WrappedPacketPlayOutUpdateSignImpl(WrappedPacketDataSerializer serializer) {
        PacketDataSerializer dataSerializer = (PacketDataSerializer) serializer;
        BlockPosition position = dataSerializer.c();
        this.location = new Vector(position.getX(), position.getY(), position.getZ());
        this.lines = new Component[4];

        for(int i = 0; i < 4; ++i) {
            try {
                this.lines[i] = AdventureUtils.asAdventure(dataSerializer.d());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Vector getLocation() {
        return location;
    }

    @Override
    public void setLocation(Vector location) {
        this.location = location;
    }

    @Override
    public Component[] getLines() {
        return lines;
    }

    @Override
    public void setLines(Component[] lines) {
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
