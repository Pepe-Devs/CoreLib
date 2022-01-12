package com.pepedevs.corelib.nms.packets;

import net.kyori.adventure.text.Component;

public interface WrappedPacketPlayOutPlayerListHeaderFooter extends WrappedPacket {
    Component getHeader();

    Component getFooter();

    void setHeader(Component header);

    void setFooter(Component footer);
}
