package com.pepedevs.radium.scoreboard;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.util.AdventureSerializer;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDisplayScoreboard;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerScoreboardObjective;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerTeams;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerUpdateScore;
import com.pepedevs.radium.adventure.AdventureUtils;
import com.pepedevs.radium.utils.version.Version;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class Scoreboard {

    private static final int MAX_DISPLAY_NAME_LENGTH = 32;
    private static final int MAX_ELEMENTS_LENGTH = 48;
    private static final ChatColor[] COLOR_CODES = ChatColor.values();

    private Component title;
    private List<Component> elements;
    private Component oldTitle;
    private List<Component> oldElements;
    private PlaceholderFunction placeholderFunction;

    private final Map<UUID, String> shown;

    public Scoreboard(String title) {
        Validate.isTrue(!(title.length() > MAX_DISPLAY_NAME_LENGTH && Version.SERVER_VERSION.isOlder(Version.v1_13_R1)),
                "Title is longer than 32 chars.");
        this.title = AdventureUtils.fromVanillaString(title);
        this.oldTitle = this.title;
        this.elements = new ArrayList<>();
        this.oldElements = new ArrayList<>();
        this.shown = new ConcurrentHashMap<>();
    }

    public Scoreboard(Component title) {
        this(AdventureUtils.toVanillaString(title));
    }

    public Component getTitle() {
        return title;
    }

    public void setTitle(Component title) {
        this.title = title;
    }

    public List<Component> getElements() {
        return Collections.unmodifiableList(this.elements);
    }

    public List<String> getElementsAsString() {
        return Collections.unmodifiableList(AdventureUtils.toVanillaString(this.elements));
    }

    public Component getLine(int line) {
        this.checkLineNumber(line, true, false);
        return this.elements.get(line);
    }

    public String getLineAsString(int line) {
        this.checkLineNumber(line, true, false);
        return AdventureUtils.toVanillaString(this.elements.get(line));
    }

    public void addLines(String... elements) {
        this.addLines(AdventureUtils.fromVanillaString(Arrays.asList(elements)));
    }

    public void addLines(Component... elements) {
        this.addLines(Arrays.asList(elements));
    }

    public void addLines(Collection<Component> elements) {
        this.checkLineNumber(this.elements.size() + elements.size(), false, true);
        if (Version.SERVER_VERSION.isOlder(Version.v1_13_R1)) {
            int lineCount = 0;
            for (Component component : elements) {
                if (component != null && this.getLength(component) > MAX_ELEMENTS_LENGTH) {
                    throw new IllegalArgumentException(
                            "Line " + lineCount + " is longer than 48 chars");
                }
                lineCount++;
            }
        }

        this.oldElements = new ArrayList<>(elements);
        this.elements.addAll(elements);
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
        this.elements.set(index, AdventureUtils.fromVanillaString(line));
    }

    public void setLine(int index, Component line) {
        this.setLine(index, AdventureUtils.toVanillaString(line));
    }

    public void removeLine(int index) {
        this.checkLineNumber(index, true, false);
        this.oldElements = new ArrayList<>(this.elements);
        this.elements.remove(index);
    }

    public void setPlaceholderFunction(PlaceholderFunction placeholderFunction) {
        this.placeholderFunction = placeholderFunction;
    }

    public Collection<UUID> getViewers() {
        return this.shown.keySet();
    }

    public void show(Player... players) {
        for (Player player : players) {
            if (this.shown.containsKey(player.getUniqueId())) continue;

            String id = "coreboard-" + ThreadLocalRandom.current().nextInt(99999);
            this.sendObjectivePacket(WrapperPlayServerScoreboardObjective.ObjectiveMode.CREATE, player, id);
            this.sendDisplayObjectivePacket(player, id);
            for (int i = 0; i < this.elements.size(); i++) {
                this.sendScorePacket(i, WrapperPlayServerUpdateScore.Action.CREATE_OR_UPDATE_ITEM, player, id);
                this.sendTeamPacket(i, WrapperPlayServerTeams.TeamMode.CREATE, player, id);
            }
            this.shown.put(player.getUniqueId(), id);
        }
    }

    public void hide(Player... players) {
        for (Player player : players) {
            if (!this.shown.containsKey(player.getUniqueId())) continue;

            String id = this.shown.get(player.getUniqueId());
            for (int i = 0; i < this.elements.size(); i++) {
                this.sendTeamPacket(i - 1, WrapperPlayServerTeams.TeamMode.REMOVE, player, id);
            }
            this.sendObjectivePacket(WrapperPlayServerScoreboardObjective.ObjectiveMode.REMOVE, player, id);
            this.shown.remove(player.getUniqueId());
        }
    }

    public void hideAll() {
        for (UUID uuid : this.getViewers()) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                this.hide(player);
            }
        }
    }

    public synchronized Scoreboard update(boolean force, Player... players) {
        for (Player player : players) {
            Validate.isTrue(
                    this.shown.containsKey(player.getUniqueId()),
                    "The player is not viewing the this scoreboard.");
            String id = this.shown.get(player.getUniqueId());
            if (force) {
                this.sendObjectivePacket(WrapperPlayServerScoreboardObjective.ObjectiveMode.UPDATE, player, id);
                for (int i = this.elements.size(); i > 0; i--) {
                    this.sendTeamPacket(i - 1, WrapperPlayServerTeams.TeamMode.REMOVE, player, id);
                    this.sendScorePacket(i - 1, WrapperPlayServerUpdateScore.Action.REMOVE_ITEM, player, id);
                }
                for (int i = 0; i < this.elements.size(); i++) {
                    this.sendScorePacket(i, WrapperPlayServerUpdateScore.Action.CREATE_OR_UPDATE_ITEM, player, id);
                    this.sendTeamPacket(i, WrapperPlayServerTeams.TeamMode.CREATE, player, id);
                }
                this.oldElements = new ArrayList<>(this.elements);
            } else {
                if (!this.oldTitle.equals(this.title))
                    this.sendObjectivePacket(WrapperPlayServerScoreboardObjective.ObjectiveMode.UPDATE, player, id);

                if (this.oldElements.size() != this.elements.size()) {
                    List<Component> oldLinesCopy = new ArrayList<>(this.oldElements);

                    if (this.oldElements.size() > this.elements.size()) {
                        for (int i = oldLinesCopy.size(); i > this.elements.size(); i--) {
                            this.sendTeamPacket(i - 1, WrapperPlayServerTeams.TeamMode.REMOVE, player, id);
                            this.sendScorePacket(i - 1, WrapperPlayServerUpdateScore.Action.REMOVE_ITEM, player, id);

                            this.oldElements.remove(0);
                        }
                    } else {
                        for (int i = oldLinesCopy.size(); i < this.elements.size(); i++) {
                            this.sendScorePacket(i, WrapperPlayServerUpdateScore.Action.CREATE_OR_UPDATE_ITEM, player, id);
                            this.sendTeamPacket(i, WrapperPlayServerTeams.TeamMode.CREATE, player, id);

                            this.oldElements.add(
                                    this.oldElements.size() - i,
                                    this.elements.get(this.elements.size() - 1 - i));
                        }
                    }
                }

                for (int i = 0; i < this.elements.size(); i++) {
                    if (!Objects.equals(
                            this.oldElements.get(this.oldElements.size() - 1 - i),
                            this.elements.get(this.elements.size() - 1 - i))) {
                        this.sendTeamPacket(i, WrapperPlayServerTeams.TeamMode.UPDATE, player, id);
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

    private void sendObjectivePacket(WrapperPlayServerScoreboardObjective.ObjectiveMode mode, Player player, String id) {
        WrapperPlayServerScoreboardObjective packet = new WrapperPlayServerScoreboardObjective(id, mode,
                Optional.of(AdventureUtils.toVanillaString(this.formatComponent(this.title, player))),
                Optional.of(WrapperPlayServerScoreboardObjective.HealthDisplay.INTEGER));
        PacketEvents.getAPI().getPlayerManager().sendPacket(player, packet);
    }

    private void sendDisplayObjectivePacket(Player player, String id) {
        WrapperPlayServerDisplayScoreboard packet = new WrapperPlayServerDisplayScoreboard(1, id);
        PacketEvents.getAPI().getPlayerManager().sendPacket(player, packet);
    }

    private void sendScorePacket(int score, WrapperPlayServerUpdateScore.Action action, Player player, String id) {
        WrapperPlayServerUpdateScore packet = new WrapperPlayServerUpdateScore(COLOR_CODES[score].toString(), action, id, Optional.of(score));
        PacketEvents.getAPI().getPlayerManager().sendPacket(player, packet);
    }

    private void sendTeamPacket(int score, WrapperPlayServerTeams.TeamMode mode, Player player, String id) {
        if (mode == WrapperPlayServerTeams.TeamMode.ADD_ENTITIES || mode == WrapperPlayServerTeams.TeamMode.REMOVE_ENTITIES) {
            throw new UnsupportedOperationException();
        }

        int maxLength = Version.SERVER_VERSION.isOlder(Version.v1_13_R1) ? 16 : 1024;

        String line = AdventureUtils.toVanillaString(this.formatComponent(this.elements.get(this.elements.size() - 1 - score), player));
        String prefix;
        String suffix = null;

        if (line == null || line.isEmpty()) {
            prefix = COLOR_CODES[score].toString() + ChatColor.RESET;
        } else if (line.length() <= maxLength) {
            prefix = line;
        } else {
            // Prevent splitting color codes
            int index =
                    line.charAt(maxLength - 1) == ChatColor.COLOR_CHAR
                            ? (maxLength - 1)
                            : maxLength;
            prefix = line.substring(0, index);
            String suffixTmp = line.substring(index);
            ChatColor chatColor = null;

            if (suffixTmp.length() >= 2 && suffixTmp.charAt(0) == ChatColor.COLOR_CHAR) {
                chatColor = ChatColor.getByChar(suffixTmp.charAt(1));
            }

            String color = ChatColor.getLastColors(prefix);
            boolean addColor = chatColor == null || chatColor.isFormat();

            suffix =
                    (addColor ? (color.isEmpty() ? ChatColor.RESET.toString() : color) : "")
                            + suffixTmp;
        }

        if (prefix.length() > maxLength || (suffix != null && suffix.length() > maxLength)) {
            // Something went wrong, just cut to prevent client crash/kick
            prefix = prefix.substring(0, maxLength);
            suffix = (suffix != null) ? suffix.substring(0, maxLength) : null;
        }

        WrapperPlayServerTeams.ScoreBoardTeamInfo info = new WrapperPlayServerTeams.ScoreBoardTeamInfo(
                AdventureSerializer.asAdventure(id + ':' + score),
                AdventureSerializer.asAdventure(prefix),
                AdventureSerializer.asAdventure(suffix == null ? "" : suffix),
                WrapperPlayServerTeams.NameTagVisibility.ALWAYS,
                WrapperPlayServerTeams.CollisionRule.ALWAYS,
                NamedTextColor.WHITE,
                WrapperPlayServerTeams.OptionData.NONE
        );
        WrapperPlayServerTeams packet = new WrapperPlayServerTeams(id, mode, Optional.of(info), Collections.singletonList(COLOR_CODES[score].toString()));
        PacketEvents.getAPI().getPlayerManager().sendPacket(player, packet);
    }

    //------------------------------

    private void checkLineNumber(int line, boolean checkInRange, boolean checkMax) {
        Validate.isTrue(line > 0, "Line number must be positive");

        if (checkInRange && line >= this.elements.size()) {
            throw new IllegalArgumentException("Line number must be under " + this.elements.size());
        }

        if (checkMax && line >= 15) {
            throw new IllegalArgumentException("Line number is too high: " + line);
        }
    }

    private int getLength(Component component) {
        return AdventureUtils.toVanillaString(component).length();
    }

    private Component formatComponent(final Component component, Player player) {
        if (this.placeholderFunction != null)
            return this.placeholderFunction.apply(player, component);
        else
            return component;
    }

    @FunctionalInterface
    public interface PlaceholderFunction {

        Component apply(Player player, Component raw);

    }
}
