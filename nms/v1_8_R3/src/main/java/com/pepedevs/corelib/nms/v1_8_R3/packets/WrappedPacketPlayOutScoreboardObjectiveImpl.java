package com.pepedevs.corelib.nms.v1_8_R3.packets;

import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutScoreboardObjective;
import com.pepedevs.corelib.nms.packets.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.v1_8_R3.NMSImpl;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardObjective;

import java.io.IOException;
import java.util.Locale;

public class WrappedPacketPlayOutScoreboardObjectiveImpl extends PacketPlayOutScoreboardObjective implements WrappedPacketPlayOutScoreboardObjective {

    public WrappedPacketPlayOutScoreboardObjectiveImpl(String name, String displayName, HealthDisplay display, ObjectiveMode mode) {
        WrappedPacketDataSerializer serializer = NMSImpl.INSTANCE.getDataSerializer();
        serializer.serializeString(name)
                .serializeByte(mode.ordinal());
        if (mode == ObjectiveMode.CREATE || mode == ObjectiveMode.UPDATE) {
            serializer.serializeString(displayName)
                    .serializeString(display.name().toLowerCase(Locale.ENGLISH));
        }

        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
