package com.pepedevs.corelib.nms.v1_12_R1.packets;

import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutScoreboardObjective;
import com.pepedevs.corelib.nms.v1_12_R1.NMSProviderImpl;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutScoreboardObjective;

import java.io.IOException;
import java.util.Locale;

public class WrappedPacketPlayOutScoreboardObjectiveImpl extends PacketPlayOutScoreboardObjective implements WrappedPacketPlayOutScoreboardObjective {

    public WrappedPacketPlayOutScoreboardObjectiveImpl(String name, String displayName, HealthDisplay display, ObjectiveMode mode) {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
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

    public WrappedPacketPlayOutScoreboardObjectiveImpl(WrappedPacketDataSerializer serializer) {
        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
