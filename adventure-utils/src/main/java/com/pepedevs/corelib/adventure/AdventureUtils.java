package com.pepedevs.corelib.adventure;

import com.google.gson.JsonParseException;
import com.pepedevs.corelib.utils.reflection.resolver.MethodResolver;
import com.pepedevs.corelib.utils.reflection.resolver.ResolverQuery;
import com.pepedevs.corelib.utils.reflection.resolver.minecraft.CraftClassResolver;
import com.pepedevs.corelib.utils.reflection.resolver.minecraft.NMSClassResolver;
import com.pepedevs.corelib.utils.reflection.resolver.wrapper.ClassWrapper;
import com.pepedevs.corelib.utils.reflection.resolver.wrapper.MethodWrapper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdventureUtils {

    private static final ClassWrapper<?> I_CHAT_BASE_COMPONENT_CLASS;
    private static final ClassWrapper<?> I_CHAT_BASE_COMPONENT_CHAT_SERIALIZER_INNER_CLASS;
    private static final ClassWrapper<?> CRAFT_CHAT_MESSAGE_CLASS;
    private static final MethodWrapper CHAT_SERIALIZER_A_METHOD_STRING;
    private static final MethodWrapper CHAT_SERIALIZER_A_METHOD_COMPONENT;
    private static final MethodWrapper CRAFT_CHAT_MESSAGE_FROM_COMPONENT_METHOD;
    private static final MethodWrapper CRAFT_CHAT_MESSAGE_FROM_STRING_METHOD;

    private static final GsonComponentSerializer GSON = GsonComponentSerializer.gson();

    static {
        NMSClassResolver NMS_CLASS_RESOLVER = new NMSClassResolver();
        CraftClassResolver CRAFT_CLASS_RESOLVER = new CraftClassResolver();
        I_CHAT_BASE_COMPONENT_CLASS = NMS_CLASS_RESOLVER.resolveWrapper("IChatBaseComponent", "net.minecraft.network.chat.IChatBaseComponent");
        I_CHAT_BASE_COMPONENT_CHAT_SERIALIZER_INNER_CLASS = NMS_CLASS_RESOLVER.resolveWrapper("IChatBaseComponent$ChatSerializer", "net.minecraft.network.chat.IChatBaseComponent$ChatSerializer");
        CRAFT_CHAT_MESSAGE_CLASS = CRAFT_CLASS_RESOLVER.resolveWrapper("util.CraftChatMessage");
        CHAT_SERIALIZER_A_METHOD_STRING = new MethodResolver(I_CHAT_BASE_COMPONENT_CHAT_SERIALIZER_INNER_CLASS.getClazz()).resolveWrapper(
                ResolverQuery.builder().with("a", String.class).build());
        CHAT_SERIALIZER_A_METHOD_COMPONENT = new MethodResolver(I_CHAT_BASE_COMPONENT_CHAT_SERIALIZER_INNER_CLASS.getClazz()).resolveWrapper(
                ResolverQuery.builder().with("a", I_CHAT_BASE_COMPONENT_CLASS.getClazz()).build());
        CRAFT_CHAT_MESSAGE_FROM_COMPONENT_METHOD = new MethodResolver(CRAFT_CHAT_MESSAGE_CLASS.getClazz()).resolveWrapper(
                ResolverQuery.builder().with("fromComponent", I_CHAT_BASE_COMPONENT_CLASS.getClazz()).build());
        CRAFT_CHAT_MESSAGE_FROM_STRING_METHOD = new MethodResolver(CRAFT_CHAT_MESSAGE_CLASS.getClazz()).resolveWrapper(
                ResolverQuery.builder().with("fromString", String.class).build());
    }

    public static String fromComponent(Component component) {
        String jsonMessage = GSON.serialize(component);
        try {
            Object chatComponent = CHAT_SERIALIZER_A_METHOD_STRING.invoke(null, jsonMessage);
            return (String) CRAFT_CHAT_MESSAGE_FROM_COMPONENT_METHOD.invoke(null, chatComponent);
        } catch (JsonParseException ignored) {
            return null;
        }
    }

    public static Component toComponent(String text) {
        if (text == null || text.isEmpty())
            return null;
        try {
            Object chatComponent = Array.get(CRAFT_CHAT_MESSAGE_FROM_STRING_METHOD.invoke(null, text), 0);
            return GSON.deserialize((String) CHAT_SERIALIZER_A_METHOD_COMPONENT.invoke(null, chatComponent));
        } catch (JsonParseException ignored) {
            return null;
        }
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