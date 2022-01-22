package com.pepedevs.radium.nms.packets;

import com.pepedevs.radium.nms.objects.WrappedPacketDataSerializer;

public interface WrappedPacket {

    String toString();

    WrappedPacketDataSerializer buildData();

    Object buildPacket();
}
