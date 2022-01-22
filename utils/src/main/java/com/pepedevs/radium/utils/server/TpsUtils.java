package com.pepedevs.radium.utils.server;

import com.pepedevs.radium.utils.reflection.accessor.FieldAccessor;
import com.pepedevs.radium.utils.reflection.bukkit.BukkitReflection;
import com.pepedevs.radium.utils.reflection.resolver.FieldResolver;

/** Class for getting the current tips per second of the running server. */
public class TpsUtils {

    public static final FieldAccessor RECENT_TPS =
            new FieldResolver(BukkitReflection.MINECRAFT_SERVER.getClazz())
                    .resolveAccessor("recentTps");

    /**
     * Gets current server ticks per second.
     *
     * <p>
     *
     * @return server current ticks per second.
     */
    public static double getTicksPerSecond() {
        final Object server = BukkitReflection.MINECRAFT_SERVER_GET_SERVER.invoke(null);
        final double[] tps = (double[]) RECENT_TPS.get(server);

        return tps[0];
    }
}
