package com.pepedevs.radium.nms.packets;

import net.kyori.adventure.text.Component;
import org.bukkit.util.Vector;

public interface WrappedPacketPlayOutUpdateSign extends WrappedPacket {
    Vector getLocation();

    void setLocation(Vector location);

    Component[] getLines();

    void setLines(Component[] lines);
}
