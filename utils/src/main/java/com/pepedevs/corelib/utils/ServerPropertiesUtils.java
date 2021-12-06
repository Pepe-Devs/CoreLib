package com.pepedevs.corelib.utils;

import com.pepedevs.corelib.utils.reflection.bukkit.BukkitReflection;
import com.pepedevs.corelib.utils.reflection.general.FieldReflection;
import com.pepedevs.corelib.utils.version.Version;

import java.util.Properties;

/** Class for dealing with the server properties (Stored in "server.properties" file). */
public class ServerPropertiesUtils {

    private static final Properties PROPERTIES;

    static {
        Properties properties = null;
        try {
            boolean newest = Version.SERVER_VERSION.isNewerEquals(Version.v1_17_R1);
            Object server = BukkitReflection.MINECRAFT_SERVER_GET_SERVER.invoke(null);
            Object property_manager =
                    FieldReflection.getValue(server, newest ? "x" : "propertyManager");

            if (Version.SERVER_VERSION.isNewerEquals(Version.v1_14_R1)) {
                Object dedicatedServerProperties =
                        FieldReflection.getValue(property_manager, newest ? "b" : "properties");
                properties =
                        (Properties)
                                FieldReflection.get(
                                                dedicatedServerProperties
                                                        .getClass()
                                                        .getSuperclass(),
                                                newest ? "X" : "properties")
                                        .get(dedicatedServerProperties);
            } else {
                properties = FieldReflection.getValue(property_manager, "properties");
            }
        } catch (IllegalAccessException
                | IllegalArgumentException
                | SecurityException
                | NoSuchFieldException ex) {
            ex.printStackTrace();
        }

        PROPERTIES = properties;
    }

    /**
     * Get property from server.properties file with String type
     *
     * <p>
     *
     * @param key Keyword to get the value
     * @param default_value Default value if not found
     * @return Value of the key
     */
    public static String getStringProperty(String key, String default_value) {
        try {
            return PROPERTIES.getProperty(key, default_value);
        } catch (Throwable ex) {
            return default_value;
        }
    }

    /**
     * Get property from server.properties file with Integer type
     *
     * <p>
     *
     * @param key Keyword to get the value
     * @param default_value Default value if not found
     * @return Value of the key
     */
    public static int getIntProperty(String key, int default_value) {
        try {
            return Integer.parseInt(PROPERTIES.getProperty(key, String.valueOf(default_value)));
        } catch (Throwable ex) {
            return default_value;
        }
    }

    /**
     * Get property from server.properties file with Long type
     *
     * <p>
     *
     * @param key Keyword to get the value
     * @param default_value Default value if not found
     * @return Value of the key
     */
    public static long getLongProperty(String key, long default_value) {
        try {
            return Long.parseLong(PROPERTIES.getProperty(key, String.valueOf(default_value)));
        } catch (Throwable ex) {
            return default_value;
        }
    }

    /**
     * Get property from server.properties file with Boolean type
     *
     * <p>
     *
     * @param key Keyword to get the value
     * @param default_value Default value if not found
     * @return Value of the key
     */
    public static boolean getBooleanProperty(String key, boolean default_value) {
        try {
            return Boolean.parseBoolean(PROPERTIES.getProperty(key, String.valueOf(default_value)));
        } catch (Throwable ex) {
            return default_value;
        }
    }
}
