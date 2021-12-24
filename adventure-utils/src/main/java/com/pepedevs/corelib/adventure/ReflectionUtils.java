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

public class ReflectionUtils {

    private static final ClassWrapper<?> I_CHAT_BASE_COMPONENT_CLASS;
    private static final ClassWrapper<?> I_CHAT_BASE_COMPONENT_CHAT_SERIALIZER_INNER_CLASS;
    private static final ClassWrapper<?> CRAFT_CHAT_MESSAGE_CLASS;
    private static final MethodWrapper CHAT_SERIALIZER_A_METHOD;
    private static final MethodWrapper CRAFT_CHAT_MESSAGE_FROM_COMPONENT_METHOD;

    private static final GsonComponentSerializer GSON  = GsonComponentSerializer.gson();

    static {
        NMSClassResolver NMS_CLASS_RESOLVER = new NMSClassResolver();
        I_CHAT_BASE_COMPONENT_CLASS = NMS_CLASS_RESOLVER.resolveWrapper("IChatBaseComponent", "net.minecraft.network.chat.IChatBaseComponent");
        I_CHAT_BASE_COMPONENT_CHAT_SERIALIZER_INNER_CLASS = NMS_CLASS_RESOLVER.resolveWrapper("IChatBaseComponent$ChatSerializer", "net.minecraft.network.chat.IChatBaseComponent$ChatSerializer");
        CRAFT_CHAT_MESSAGE_CLASS = new CraftClassResolver().resolveWrapper("util.CraftChatMessage");
        CHAT_SERIALIZER_A_METHOD = new MethodResolver(I_CHAT_BASE_COMPONENT_CHAT_SERIALIZER_INNER_CLASS.getClazz()).resolveWrapper(ResolverQuery.builder().with("a", String.class).build());
        CRAFT_CHAT_MESSAGE_FROM_COMPONENT_METHOD = new MethodResolver(CRAFT_CHAT_MESSAGE_CLASS.getClazz()).resolveWrapper(ResolverQuery.builder().with("fromComponent", I_CHAT_BASE_COMPONENT_CLASS.getClazz()).build());
    }

    public static String fromComponent(Component component) {
        String jsonMessage = GSON.serialize(component);
        try{
            Object object = object = CHAT_SERIALIZER_A_METHOD.invoke(null, jsonMessage);
            return (String) CRAFT_CHAT_MESSAGE_FROM_COMPONENT_METHOD.invoke(null, object);
        }catch (JsonParseException ignored) {return null;}
    }



}
