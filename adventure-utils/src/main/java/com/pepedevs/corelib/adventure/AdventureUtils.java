package com.pepedevs.corelib.adventure;

import com.google.gson.Gson;
import com.pepedevs.corelib.nms.NMSBridge;
import com.pepedevs.corelib.utils.reflection.resolver.FieldResolver;
import com.pepedevs.corelib.utils.reflection.resolver.minecraft.NMSClassResolver;
import com.pepedevs.corelib.utils.reflection.resolver.wrapper.ClassWrapper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.translation.GlobalTranslator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class AdventureUtils {

    private static final ClassWrapper<?> I_CHAT_BASE_COMPONENT_CLASS;

    private static final Gson NMS_GSON;
    private static final GsonComponentSerializer GSON = GsonComponentSerializer.gson();

    static {
        NMSClassResolver NMS_CLASS_RESOLVER = new NMSClassResolver();
        I_CHAT_BASE_COMPONENT_CLASS = NMS_CLASS_RESOLVER.resolveWrapper("IChatBaseComponent", "net.minecraft.network.chat.IChatBaseComponent");
        ClassWrapper<?> I_CHAT_BASE_COMPONENT_CHAT_SERIALIZER_INNER_CLASS = NMS_CLASS_RESOLVER.resolveWrapper("IChatBaseComponent$ChatSerializer", "net.minecraft.network.chat.IChatBaseComponent$ChatSerializer");
        NMS_GSON = new FieldResolver(I_CHAT_BASE_COMPONENT_CHAT_SERIALIZER_INNER_CLASS.getClazz()).resolveAccessor("a").get(null);
    }

    public static Component asAdventure(final Object iChatBaseComponent) {
        return iChatBaseComponent == null ? null : GSON.serializer().fromJson(NMS_GSON.toJsonTree(iChatBaseComponent), Component.class);
    }

    public static ArrayList<Component> asAdventure(final List<Object> iChatBaseComponent) {
        final ArrayList<Component> adventures = new ArrayList<>(iChatBaseComponent.size());
        for (final Object component : iChatBaseComponent) {
            adventures.add(asAdventure(component));
        }
        return adventures;
    }

    public static ArrayList<Component> asAdventureFromJson(final List<String> jsonStrings) {
        final ArrayList<Component> adventures = new ArrayList<>(jsonStrings.size());
        for (final String json : jsonStrings) {
            adventures.add(GSON.deserialize(json));
        }
        return adventures;
    }

    public static List<String> asJson(final List<Component> adventures) {
        final List<String> jsons = new ArrayList<>(adventures.size());
        for (final Component component : adventures) {
            jsons.add(GSON.serialize(component));
        }
        return jsons;
    }

    public static Object asVanilla(final Component component) {
        return NMS_GSON.fromJson(GSON.serializer().toJsonTree(component), I_CHAT_BASE_COMPONENT_CLASS.getClazz());
    }

    public static List<Object> asVanilla(final List<Component> adventures) {
        final List<Object> vanillas = new ArrayList<>(adventures.size());
        for (final Component adventure : adventures) {
            vanillas.add(asVanilla(adventure));
        }
        return vanillas;
    }

    public static String toVanillaString(Component component) {
        Object chatComponent = asVanilla(component);
        return NMSBridge.getNMSProvider().craftChatMessageFromComponent(chatComponent);
    }

    public static List<String> toVanillaString(List<Component> components) {
        final List<String> vanillas = new ArrayList<>(components.size());
        for (final Component component : components) {
            vanillas.add(toVanillaString(component));
        }
        return vanillas;
    }

    public static Component fromVanillaString(String text) {
        if (text == null || text.isEmpty())
            return null;
        Object chatComponent = NMSBridge.getNMSProvider().craftChatMessageFromString(text)[0];
        return asAdventure(chatComponent);
    }

    public static List<Component> fromVanillaString(List<String> texts) {
        final List<Component> components = new ArrayList<>(texts.size());
        for (final String text : texts) {
            components.add(fromVanillaString(text));
        }
        return components;
    }

    public static String asJsonString(final Component component, final Locale locale) {
        return GSON.serialize(GlobalTranslator.render(component, locale != null ? locale : Locale.US));
    }

    public static String asJsonString(final Object iChatBaseComponent) {
        return NMS_GSON.toJson(iChatBaseComponent);
    }

    public static Component fromLegacyText(String text) {
        if (text.contains("§")) {
            return fromLegacyText('§', text);
        } else {
            return fromLegacyText('&', text);
        }
    }

    public static List<Component> fromLegacyText(Collection<String> text) {
        List<Component> components = new ArrayList<>();
        for (String line : text) {
            components.add(fromLegacyText(line));
        }
        return components;
    }

    public static Component[] fromLegacyText(String... text) {
        Component[] components = new Component[text.length];
        for (int i = 0; i < text.length; i++) {
            components[i] = fromLegacyText(text[i]);
        }
        return components;
    }

    public static Component fromLegacyText(char legacyCharacter, String text) {
        return LegacyComponentSerializer.legacy(legacyCharacter).deserialize(text);
    }

    public static List<Component> fromLegacyText(char legacyCharacter, Collection<String> text) {
        List<Component> components = new ArrayList<>();
        for (String line : text) {
            components.add(fromLegacyText(legacyCharacter, line));
        }
        return components;
    }

    public static Component[] fromLegacyText(char legacyCharacter, String... text) {
        Component[] components = new Component[text.length];
        for (int i = 0; i < text.length; i++) {
            components[i] = fromLegacyText(legacyCharacter, text[i]);
        }
        return components;
    }

    public static String toLegacyText(char legacyCharacter, Component text) {
        return LegacyComponentSerializer.legacy(legacyCharacter).serialize(text);
    }

    public static List<String> toLegacyText(char legacyCharacter, Collection<Component> text) {
        List<String> components = new ArrayList<>();
        for (Component component : text) {
            components.add(toLegacyText(legacyCharacter, component));
        }
        return components;
    }

    public static String[] toLegacyText(char legacyCharacter, Component... text) {
        String[] components = new String[text.length];
        for (int i = 0; i < text.length; i++) {
            components[i] = toLegacyText(legacyCharacter, text[i]);
        }
        return components;
    }

}