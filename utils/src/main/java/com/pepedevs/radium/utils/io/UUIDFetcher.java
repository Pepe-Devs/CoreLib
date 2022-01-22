package com.pepedevs.radium.utils.io;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.util.UUIDTypeAdapter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UUIDFetcher {

    private static final String UUID_URL =
            "https://api.mojang.com/users/profiles/minecraft/%s?at=%d";
    private static final String NAME_URL = "https://api.mojang.com/user/profiles/%s/names";
    private final Map<String, UUID> uuidCache = new ConcurrentHashMap<>();
    private final Map<UUID, String> nameCache = new ConcurrentHashMap<>();

    private static final UUIDFetcher INSTANCE = new UUIDFetcher();

    private UUIDFetcher() {}

    public UUID getUUID(String name) {
        return getUUIDAt(name, System.currentTimeMillis());
    }

    public UUID getUUIDAt(String name, long timestamp) {
        name = name.toLowerCase();

        // Check for cached unique id
        if (uuidCache.containsKey(name)) return uuidCache.get(name);

        try {
            // Open connection
            HttpURLConnection connection =
                    (HttpURLConnection)
                            new URL(String.format(UUID_URL, name, timestamp / 1000))
                                    .openConnection();
            connection.setReadTimeout(5000);

            // Parse response
            try (BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(connection.getInputStream()))) {

                try {
                    // Parse response
                    JsonObject data = new JsonParser().parse(bufferedReader).getAsJsonObject();
                    UUID uniqueId =
                            UUID.fromString(
                                    data.get("id")
                                            .getAsString()
                                            .replaceFirst(
                                                    "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})",
                                                    "$1-$2-$3-$4-$5"));

                    // Cache data
                    uuidCache.put(name, uniqueId);
                    nameCache.put(uniqueId, data.get("name").getAsString());

                    return uniqueId;
                } catch (VerifyError | IllegalStateException ignore) {
                }
            }
        } catch (Exception ignored) {
        }

        return null;
    }

    public String getName(String name, UUID uuid) {
        // Check for cached name
        if (nameCache.containsKey(uuid)) return nameCache.get(uuid);

        try {
            // Open connection
            HttpURLConnection connection =
                    (HttpURLConnection)
                            new URL(String.format(NAME_URL, UUIDTypeAdapter.fromUUID(uuid)))
                                    .openConnection();
            connection.setReadTimeout(5000);

            // Parse response
            try (BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(connection.getInputStream()))) {

                JsonArray data = new JsonParser().parse(bufferedReader).getAsJsonArray();
                JsonObject currentNameData = data.get(data.size() - 1).getAsJsonObject();

                // Cache data
                uuidCache.put(currentNameData.get("name").getAsString().toLowerCase(), uuid);
                nameCache.put(uuid, currentNameData.get("name").getAsString());

                return currentNameData.get("name").getAsString();
            }
        } catch (Exception ignore) {

        }

        return name;
    }

    public static UUIDFetcher getInstance() {
        return INSTANCE;
    }
}
