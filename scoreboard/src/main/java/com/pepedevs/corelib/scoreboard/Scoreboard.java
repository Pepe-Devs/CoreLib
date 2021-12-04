package com.pepedevs.corelib.scoreboard;

import com.pepedevs.corelib.utils.reflection.PacketConstant;
import com.pepedevs.corelib.utils.reflection.accessor.FieldAccessor;
import com.pepedevs.corelib.utils.reflection.bukkit.BukkitReflection;
import com.pepedevs.corelib.utils.reflection.general.EnumReflection;
import com.pepedevs.corelib.utils.reflection.resolver.ConstructorResolver;
import com.pepedevs.corelib.utils.reflection.resolver.MethodResolver;
import com.pepedevs.corelib.utils.reflection.resolver.ResolverQuery;
import com.pepedevs.corelib.utils.reflection.resolver.minecraft.CraftClassResolver;
import com.pepedevs.corelib.utils.reflection.resolver.minecraft.NMSClassResolver;
import com.pepedevs.corelib.utils.reflection.resolver.wrapper.ClassWrapper;
import com.pepedevs.corelib.utils.reflection.resolver.wrapper.ConstructorWrapper;
import com.pepedevs.corelib.utils.reflection.resolver.wrapper.MethodWrapper;
import com.pepedevs.corelib.utils.version.Version;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class Scoreboard {

    private static final Map<ClassWrapper<?>, FieldAccessor[]> PACKETS = new HashMap<>(8);
    private static final String[] COLOR_CODES = Arrays.stream(ChatColor.values())
            .map(Object::toString)
            .toArray(String[]::new);
    // Packets and components
    private static final ClassWrapper<?> CHAT_COMPONENT_CLASS;
    private static final ClassWrapper<?> CHAT_FORMAT_ENUM;
    private static final Object EMPTY_MESSAGE;
    private static final Object RESET_FORMATTING;
    private static final MethodWrapper MESSAGE_FROM_STRING;
    // Scoreboard packets
    private static final ConstructorWrapper<?> PACKET_SB_OBJ;
    private static final ConstructorWrapper<?> PACKET_SB_DISPLAY_OBJ;
    private static final ConstructorWrapper<?> PACKET_SB_SCORE;
    private static final ConstructorWrapper<?> PACKET_SB_TEAM;
    private static final ConstructorWrapper<?> PACKET_SB_SERIALIZABLE_TEAM;
    // Scoreboard enums
    private static final ClassWrapper<?> ENUM_SB_HEALTH_DISPLAY;
    private static final ClassWrapper<?> ENUM_SB_ACTION;
    private static final Object ENUM_SB_HEALTH_DISPLAY_INTEGER;
    private static final Object ENUM_SB_ACTION_CHANGE;
    private static final Object ENUM_SB_ACTION_REMOVE;

    static {
        CraftClassResolver craftClassResolver = new CraftClassResolver();
        NMSClassResolver nmsClassResolver = new NMSClassResolver();
        ClassWrapper<?> craftChatMessageClass = craftClassResolver.resolveWrapper("util.CraftChatMessage");
        ClassWrapper<?> sbTeamClass = nmsClassResolver.resolveWrapper(PacketConstant.PACKET_PLAY_OUT_SCOREBOARD_TEAM.getClazz().getName() + "$b");

        MESSAGE_FROM_STRING = new MethodResolver(craftChatMessageClass.getClazz()).resolveWrapper(
                ResolverQuery.builder().with("fromString", String.class).build());
        CHAT_COMPONENT_CLASS = nmsClassResolver.resolveWrapper("IChatBaseComponent", "net.minecraft.network.chat.IChatBaseComponent");
        CHAT_FORMAT_ENUM = nmsClassResolver.resolveWrapper("EnumChatFormat");
        EMPTY_MESSAGE = Array.get(MESSAGE_FROM_STRING.invoke(null, ""), 0);
        RESET_FORMATTING = EnumReflection.getEnumConstant(CHAT_FORMAT_ENUM.getClazz().asSubclass(Enum.class), "RESET", 21);
        PACKET_SB_OBJ = new ConstructorResolver(PacketConstant.PACKET_PLAY_OUT_SCOREBOARD_OBJECTIVE.getClazz()).resolveWrapper(new Class[0]);
        PACKET_SB_DISPLAY_OBJ = new ConstructorResolver(PacketConstant.PACKET_PLAY_OUT_SCOREBOARD_DISPLAY_OBJECTIVE.getClazz()).resolveWrapper(new Class[0]);
        PACKET_SB_SCORE = new ConstructorResolver(PacketConstant.PACKET_PLAY_OUT_SCOREBOARD_SCORE.getClazz()).resolveWrapper(new Class[0]);
        PACKET_SB_TEAM = new ConstructorResolver(PacketConstant.PACKET_PLAY_OUT_SCOREBOARD_TEAM.getClazz()).resolveWrapper(new Class[0]);
        PACKET_SB_SERIALIZABLE_TEAM = sbTeamClass == null ? null : new ConstructorResolver(sbTeamClass.getClazz()).resolveWrapper(new Class[0]);

        for (ClassWrapper<?> clazz : new ClassWrapper<?>[]{PacketConstant.PACKET_PLAY_OUT_SCOREBOARD_OBJECTIVE, PacketConstant.PACKET_PLAY_OUT_SCOREBOARD_DISPLAY_OBJECTIVE,
                PacketConstant.PACKET_PLAY_OUT_SCOREBOARD_SCORE, PacketConstant.PACKET_PLAY_OUT_SCOREBOARD_TEAM, sbTeamClass}) {
            if (clazz == null)
                continue;
            FieldAccessor[] fields = Arrays.stream(clazz.getClazz().getDeclaredFields())
                    .map(FieldAccessor::new)
                    .filter(field -> !field.isStatic())
                    .toArray(FieldAccessor[]::new);
            PACKETS.put(clazz, fields);
        }

        String enumSbActionClass = Version.SERVER_VERSION.isNewerEquals(Version.v1_13_R1)
                ? "ScoreboardServer$Action"
                : "PacketPlayOutScoreboardScore$EnumScoreboardAction";
        ENUM_SB_HEALTH_DISPLAY = nmsClassResolver.resolveWrapper("IScoreboardCriteria$EnumScoreboardHealthDisplay",
                "net.minecraft.world.scores.criteria.IScoreboardCriteria$EnumScoreboardHealthDisplay");
        ENUM_SB_ACTION = nmsClassResolver.resolveWrapper(enumSbActionClass, "net.minecraft.server." + enumSbActionClass);
        ENUM_SB_HEALTH_DISPLAY_INTEGER = EnumReflection.getEnumConstant(ENUM_SB_HEALTH_DISPLAY.getClazz().asSubclass(Enum.class), "INTEGER", 0);
        ENUM_SB_ACTION_CHANGE = EnumReflection.getEnumConstant(ENUM_SB_ACTION.getClazz().asSubclass(Enum.class), "CHANGE", 0);
        ENUM_SB_ACTION_REMOVE = EnumReflection.getEnumConstant(ENUM_SB_ACTION.getClazz().asSubclass(Enum.class), "REMOVE", 1);
    }

    private static final int MAX_DISPLAY_NAME_LENGTH = 32;
    private static final int MAX_ELEMENTS_LENGTH = 40;

    private String title;
    private List<String> elements;
    private String oldTitle;
    private List<String> oldElements;

    private final Map<UUID, String> shown;

    public Scoreboard(String title, String... elements) {
        this(title, Arrays.asList(elements));
    }

    public Scoreboard(String title, List<String> elements) {
        Validate.isTrue(!(title.length() > MAX_DISPLAY_NAME_LENGTH && Version.SERVER_VERSION.isOlder(Version.v1_13_R1)),
                "Title is longer than 32 chars.");

        this.title = title;
        this.oldTitle = title;
        this.elements = new LinkedList<>();
        this.oldElements = new ArrayList<>();
        this.shown = new ConcurrentHashMap<>();
    }

    public String getTitle() {
        return this.title;
    }

    public Scoreboard setTitle(String title) {
        this.title = title;
        return this;
    }

    public List<String> getElements() {
        return Collections.unmodifiableList(this.elements);
    }

    public String getLine(int line) {
        this.checkLineNumber(line, true, false);
        return this.elements.get(line);
    }

    public Scoreboard addLines(String... elements) {
        return this.addLines(Arrays.asList(elements));
    }

    public Scoreboard addLines(Collection<String> elements) {
        this.checkLineNumber(this.elements.size() + elements.size(), false, true);
        if (Version.SERVER_VERSION.isOlder(Version.v1_13_R1)) {
            int lineCount = 0;
            for (String s : elements) {
                if (s != null && s.length() > MAX_ELEMENTS_LENGTH) {
                    throw new IllegalArgumentException("Line " + lineCount + " is longer than 40 chars");
                }
                lineCount++;
            }
        }

        this.oldElements = new ArrayList<>(elements);
        this.elements.addAll(elements);
        return this;
    }

    public void setLine(int index, String line) {
        this.checkLineNumber(index, true, false);
        this.checkLineNumber(this.elements.size() + elements.size(), false, true);
        if (Version.SERVER_VERSION.isOlder(Version.v1_13_R1)) {
            if (line != null && line.length() > MAX_ELEMENTS_LENGTH) {
                throw new IllegalArgumentException("Line `" + line + "` is longer than 40 chars");
            }
        }
        this.oldElements = new ArrayList<>(this.elements);
        this.elements.set(index, line);
    }

    public void removeLine(int index) {
        this.checkLineNumber(index, true, false);
        this.oldElements = new ArrayList<>(this.elements);
        this.elements.remove(index);
    }

    public void show(Player... players) {
        for (Player player : players) {
            if (this.shown.containsKey(player.getUniqueId()))
                continue;

            String id = "coreboard-" + Integer.toHexString(ThreadLocalRandom.current().nextInt());
            this.sendObjectivePacket(ObjectiveMode.CREATE, player, id);
            this.sendDisplayObjectivePacket(player, id);
            for (int i = 0; i < this.elements.size(); i++) {
                this.sendScorePacket(i, ScoreboardAction.CHANGE, player, id);
                this.sendTeamPacket(i, TeamMode.CREATE, player, id);
            }
            this.shown.put(player.getUniqueId(), id);
        }
    }

    public void hide(Player... players) {
        for (Player player : players) {
            if (!this.shown.containsKey(player.getUniqueId()))
                continue;

            String id = this.shown.get(player.getUniqueId());
            for (int i = 0; i < this.elements.size(); i++) {
                this.sendTeamPacket(i - 1, TeamMode.REMOVE, player, id);
            }
            this.sendObjectivePacket(ObjectiveMode.REMOVE, player, id);
            this.shown.remove(player.getUniqueId());
        }
    }

    public synchronized Scoreboard update(boolean force, Player... players) {
        for (Player player : players) {
            Validate.isTrue(this.shown.containsKey(player.getUniqueId()), "The player is not viewing the this scoreboard.");
            String id = this.shown.get(player.getUniqueId());
            if (force) {
                this.sendObjectivePacket(ObjectiveMode.UPDATE, player, id);
                for (int i = this.elements.size(); i > 0; i--) {
                    this.sendTeamPacket(i - 1, TeamMode.REMOVE, player, id);
                    this.sendScorePacket(i - 1, ScoreboardAction.REMOVE, player, id);
                }
                for (int i = 0; i < this.elements.size(); i++) {
                    this.sendScorePacket(i, ScoreboardAction.CHANGE, player, id);
                    this.sendTeamPacket(i, TeamMode.CREATE, player, id);
                }
                this.oldElements = new ArrayList<>(this.elements);
            } else {
                if (!this.oldTitle.equals(this.title))
                    this.sendObjectivePacket(ObjectiveMode.UPDATE, player, id);

                if (this.oldElements.size() != this.elements.size()) {
                    List<String> oldLinesCopy = new ArrayList<>(this.oldElements);

                    if (this.oldElements.size() > this.elements.size()) {
                        for (int i = oldLinesCopy.size(); i > this.elements.size(); i--) {
                            this.sendTeamPacket(i - 1, TeamMode.REMOVE, player, id);
                            this.sendScorePacket(i - 1, ScoreboardAction.REMOVE, player, id);

                            this.oldElements.remove(0);
                        }
                    } else {
                        for (int i = oldLinesCopy.size(); i < this.elements.size(); i++) {
                            this.sendScorePacket(i, ScoreboardAction.CHANGE, player, id);
                            this.sendTeamPacket(i, TeamMode.CREATE, player, id);

                            this.oldElements.add(this.oldElements.size() - i, this.elements.get(this.elements.size() - 1 - i));
                        }
                    }
                }

                for (int i = 0; i < this.elements.size(); i++) {
                    if (!Objects.equals(this.oldElements.get(this.oldElements.size() - 1 - i), this.elements.get(this.elements.size() - 1 - i))) {
                        this.sendTeamPacket(i, TeamMode.UPDATE, player, id);
                    }
                }
            }

            this.oldTitle = title;
        }

        return this;
    }

    public synchronized Scoreboard updateAll(boolean force) {
        for (UUID uuid : this.shown.keySet()) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                this.update(force, player);
            }
        }
        return this;
    }

    private void checkLineNumber(int line, boolean checkInRange, boolean checkMax) {
        Validate.isTrue(line > 0, "Line number must be positive");

        if (checkInRange && line >= this.elements.size()) {
            throw new IllegalArgumentException("Line number must be under " + this.elements.size());
        }

        if (checkMax && line >= COLOR_CODES.length - 1) {
            throw new IllegalArgumentException("Line number is too high: " + line);
        }
    }

    private void sendObjectivePacket(ObjectiveMode mode, Player player, String id) {
        Object packet = PACKET_SB_OBJ.newInstance();

        this.updatePacketField(packet, String.class, id);
        this.updatePacketField(packet, int.class, mode.ordinal());

        if (mode != ObjectiveMode.REMOVE) {
            this.setComponentField(packet, this.title, 1);

            this.updatePacketField(packet, ENUM_SB_HEALTH_DISPLAY.getClazz(), ENUM_SB_HEALTH_DISPLAY_INTEGER);
        }

        BukkitReflection.sendPacket(player, packet);
    }

    private void sendDisplayObjectivePacket(Player player, String id) {
        Object packet = PACKET_SB_DISPLAY_OBJ.newInstance();

        this.updatePacketField(packet, int.class, 1); // Position (1: sidebar)
        this.updatePacketField(packet, String.class, id); // Score Name

        BukkitReflection.sendPacket(player, packet);
    }

    private void sendScorePacket(int score, ScoreboardAction action, Player player, String id) {
        Object packet = PACKET_SB_SCORE.newInstance();

        this.updatePacketField(packet, String.class, COLOR_CODES[score], 0); // Player Name
        this.updatePacketField(packet, ENUM_SB_ACTION.getClazz(), action == ScoreboardAction.REMOVE ? ENUM_SB_ACTION_REMOVE : ENUM_SB_ACTION_CHANGE);

        if (action == ScoreboardAction.CHANGE) {
            this.updatePacketField(packet, String.class, id, 1); // Objective Name
            this.updatePacketField(packet, int.class, score); // Score
        }

        BukkitReflection.sendPacket(player, packet);
    }

    private void sendTeamPacket(int score, TeamMode mode, Player player, String id) {
        if (mode == TeamMode.ADD_PLAYERS || mode == TeamMode.REMOVE_PLAYERS) {
            throw new UnsupportedOperationException();
        }

        int maxLength = Version.SERVER_VERSION.isOlder(Version.v1_13_R1) ? 16 : 1024;
        Object packet = PACKET_SB_TEAM.newInstance();

        this.updatePacketField(packet, String.class, id + ':' + score); // Team name
        this.updatePacketField(packet, int.class, mode.ordinal(), Version.SERVER_VERSION.equalsVersion(Version.v1_8_R3) ? 1 : 0); // Update mode

        if (mode == TeamMode.CREATE || mode == TeamMode.UPDATE) {
            String line = this.elements.get(this.elements.size() - 1 - score);
            String prefix;
            String suffix = null;

            if (line == null || line.isEmpty()) {
                prefix = COLOR_CODES[score] + ChatColor.RESET;
            } else if (line.length() <= maxLength) {
                prefix = line;
            } else {
                // Prevent splitting color codes
                int index = line.charAt(maxLength - 1) == ChatColor.COLOR_CHAR ? (maxLength - 1) : maxLength;
                prefix = line.substring(0, index);
                String suffixTmp = line.substring(index);
                ChatColor chatColor = null;

                if (suffixTmp.length() >= 2 && suffixTmp.charAt(0) == ChatColor.COLOR_CHAR) {
                    chatColor = ChatColor.getByChar(suffixTmp.charAt(1));
                }

                String color = ChatColor.getLastColors(prefix);
                boolean addColor = chatColor == null || chatColor.isFormat();

                suffix = (addColor ? (color.isEmpty() ? ChatColor.RESET.toString() : color) : "") + suffixTmp;
            }

            if (prefix.length() > maxLength || (suffix != null && suffix.length() > maxLength)) {
                // Something went wrong, just cut to prevent client crash/kick
                prefix = prefix.substring(0, maxLength);
                suffix = (suffix != null) ? suffix.substring(0, maxLength) : null;
            }

            if (Version.SERVER_VERSION.isNewerEquals(Version.v1_17_R1)) {
                Object team = PACKET_SB_SERIALIZABLE_TEAM.newInstance();
                // Since the packet is initialized with null values, we need to change more things.
                this.setComponentField(team, "", 0); // Display name
                this.updatePacketField(team, CHAT_FORMAT_ENUM.getClazz(), RESET_FORMATTING); // Color
                this.setComponentField(team, prefix, 1); // Prefix
                this.setComponentField(team, suffix == null ? "" : suffix, 2); // Suffix
                this.updatePacketField(team, String.class, "always", 0); // Visibility
                this.updatePacketField(team, String.class, "always", 1); // Collisions
                this.updatePacketField(packet, Optional.class, Optional.of(team));
            } else {
                this.setComponentField(packet, prefix, 2); // Prefix
                this.setComponentField(packet, suffix == null ? "" : suffix, 3); // Suffix
                this.updatePacketField(packet, String.class, "always", 4); // Visibility for 1.8+
                this.updatePacketField(packet, String.class, "always", 5); // Collisions for 1.9+
            }

            if (mode == TeamMode.CREATE) {
                this.updatePacketField(packet, Collection.class, Collections.singletonList(COLOR_CODES[score])); // Players in the team
            }
        }

        BukkitReflection.sendPacket(player, packet);
    }


    private void updatePacketField(Object object, Class<?> fieldType, Object value) {
        this.updatePacketField(object, fieldType, value, 0);
    }

    private void updatePacketField(Object packet, Class<?> fieldType, Object value, int count) {
        int i = 0;
        for (FieldAccessor field : PACKETS.get(new ClassWrapper<>(packet.getClass()))) {
            if (field.getType() == fieldType && count == i++) {
                field.set(packet, value);
            }
        }
    }

    private void setComponentField(Object packet, String value, int count) {
        if (!Version.SERVER_VERSION.isNewerEquals(Version.v1_13_R1)) {
            this.updatePacketField(packet, String.class, value, count);
            return;
        }

        int i = 0;
        for (FieldAccessor field : PACKETS.get(new ClassWrapper<>(packet.getClass()))) {
            if ((field.getType() == String.class || field.getType() == CHAT_COMPONENT_CLASS.getClazz()) && count == i++) {
                field.set(packet, value.isEmpty() ? EMPTY_MESSAGE : Array.get(MESSAGE_FROM_STRING.invoke(value), 0));
            }
        }
    }

    enum TeamMode {
        CREATE, REMOVE, UPDATE, ADD_PLAYERS, REMOVE_PLAYERS
    }

    enum ScoreboardAction {
        CHANGE, REMOVE
    }

    enum ObjectiveMode {
        CREATE, REMOVE, UPDATE,
        ;
    }

}
