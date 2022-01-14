package com.pepedevs.corelib.utils.reflection.bukkit;

import com.pepedevs.corelib.utils.math.collision.BoundingBox;
import com.pepedevs.corelib.utils.reflection.PacketConstant;
import com.pepedevs.corelib.utils.reflection.accessor.FieldAccessor;
import com.pepedevs.corelib.utils.reflection.general.MethodReflection;
import com.pepedevs.corelib.utils.reflection.resolver.ConstructorResolver;
import com.pepedevs.corelib.utils.reflection.resolver.FieldResolver;
import com.pepedevs.corelib.utils.reflection.resolver.MethodResolver;
import com.pepedevs.corelib.utils.reflection.resolver.ResolverQuery;
import com.pepedevs.corelib.utils.reflection.resolver.minecraft.NMSClassResolver;
import com.pepedevs.corelib.utils.reflection.resolver.wrapper.ClassWrapper;
import com.pepedevs.corelib.utils.reflection.resolver.wrapper.ConstructorWrapper;
import com.pepedevs.corelib.utils.reflection.resolver.wrapper.MethodWrapper;
import com.pepedevs.corelib.utils.version.Version;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicInteger;

/** Class for reflecting Bukkit entities */
public class EntityReflection {

    public static final ClassWrapper<?> NMS_ENTITY_CLASS;
    public static final ClassWrapper<?> ENTITY_HUMAN;
    public static final ClassWrapper<?> ENTITY_LIVING;
    public static final ClassWrapper<?> ENTITY_ARMOR_STAND;
    public static final ClassWrapper<?> NBT_TAG_COMPOUND;
    public static final ClassWrapper<?> DAMAGE_SOURCE;
    public static final ConstructorWrapper<?> PACKET_PLAY_OUT_ENTITY_DESTROY_CONSTRUCTOR;
    public static final ConstructorWrapper<?> NBT_TAG_COMPOUND_CONSTRUCTOR;
    public static final MethodWrapper<?> NMS_ENTITY_GET_BOUNDING_BOX;
    public static final MethodWrapper<Float> NMS_ENTITY_GET_HEAD_HEIGHT;
    public static final MethodWrapper<Double> NMS_ENTITY_LOC_X;
    public static final MethodWrapper<Double> NMS_ENTITY_LOC_Y;
    public static final MethodWrapper<Double> NMS_ENTITY_LOC_Z;
    public static final MethodWrapper<?> NMS_ENTITY_SET_LOCATION;
    public static final MethodWrapper<?> NMS_ENTITY_SET_YAW_PITCH;
    public static final MethodWrapper<Boolean> NMS_ENTITY_IS_INVISIBLE;
    public static final MethodWrapper<?> NMS_ENTITY_SET_INVISIBLE;
    public static final MethodWrapper<Boolean> NMS_ENTITY_IS_SILENT;
    public static final MethodWrapper<?> NMS_ENTITY_SET_SILENT;
    public static final MethodWrapper<Boolean> NMS_ENTITY_IS_INVULNERABLE;
    public static final MethodWrapper<?> NBT_TAG_COMPOUND_SET_INT;
    public static final MethodWrapper<?> ENTITY_LIVING_C;
    public static final MethodWrapper<?> ENTITY_LIVING_F;
    public static final FieldAccessor NMS_ENTITY_YAW;
    public static final FieldAccessor NMS_ENTITY_PITCH;
    public static final FieldAccessor NMS_ENTITY_INVULNERABLE;
    public static final FieldAccessor ENTITY_ARMOR_STAND_INVULNERABLE;
    public static final FieldAccessor DAMAGE_SOURCE_GENERIC;
    public static FieldAccessor ENTITY_COUNTER_FIELD;

    static {
        NMSClassResolver nmsClassResolver = new NMSClassResolver();
        NMS_ENTITY_CLASS =
                nmsClassResolver.resolveWrapper("Entity", "net.minecraft.world.entity.Entity");
        ENTITY_HUMAN =
                nmsClassResolver.resolveWrapper(
                        "EntityHuman", "net.minecraft.world.entity.player.EntityHuman");
        ENTITY_LIVING =
                nmsClassResolver.resolveWrapper(
                        "EntityLiving", "net.minecraft.world.entity.EntityLiving");
        ENTITY_ARMOR_STAND =
                nmsClassResolver.resolveWrapper(
                        "EntityArmorStand",
                        "net.minecraft.world.entity.decoration.EntityArmorStand");
        NBT_TAG_COMPOUND =
                nmsClassResolver.resolveWrapper(
                        "NBTTagCompound", "net.minecraft.nbt.NBTTagCompound");
        DAMAGE_SOURCE =
                nmsClassResolver.resolveWrapper(
                        "DamageSource", "net.minecraft.world.damagesource.DamageSource");
        PACKET_PLAY_OUT_ENTITY_DESTROY_CONSTRUCTOR =
                new ConstructorResolver(PacketConstant.PACKET_PLAY_OUT_ENTITY_DESTROY.getClazz())
                        .resolveWrapper(new Class<?>[] {int[].class});
        NBT_TAG_COMPOUND_CONSTRUCTOR =
                new ConstructorResolver(NBT_TAG_COMPOUND.getClazz()).resolveWrapper(new Class[0]);
        NMS_ENTITY_GET_BOUNDING_BOX =
                new MethodResolver(NMS_ENTITY_CLASS.getClazz()).resolveWrapper("getBoundingBox");
        NMS_ENTITY_GET_HEAD_HEIGHT =
                new MethodResolver(NMS_ENTITY_CLASS.getClazz()).resolveWrapper("getHeadHeight");
        NMS_ENTITY_LOC_X = new MethodResolver(NMS_ENTITY_CLASS.getClazz()).resolveWrapper("locX");
        NMS_ENTITY_LOC_Y = new MethodResolver(NMS_ENTITY_CLASS.getClazz()).resolveWrapper("locY");
        NMS_ENTITY_LOC_Z = new MethodResolver(NMS_ENTITY_CLASS.getClazz()).resolveWrapper("locZ");
        NMS_ENTITY_SET_LOCATION =
                new MethodResolver(NMS_ENTITY_CLASS.getClazz())
                        .resolveWrapper(
                                ResolverQuery.builder()
                                        .with(
                                                "setLocation",
                                                double.class,
                                                double.class,
                                                double.class,
                                                float.class,
                                                float.class)
                                        .build());
        NMS_ENTITY_SET_YAW_PITCH =
                new MethodResolver(NMS_ENTITY_CLASS.getClazz())
                        .resolveWrapper(
                                ResolverQuery.builder()
                                        .with("setYawPitch", float.class, float.class)
                                        .build());
        NMS_ENTITY_IS_INVISIBLE =
                new MethodResolver(NMS_ENTITY_CLASS.getClazz()).resolveWrapper("isInvisible");
        NMS_ENTITY_SET_INVISIBLE =
                new MethodResolver(NMS_ENTITY_CLASS.getClazz())
                        .resolveWrapper(
                                ResolverQuery.builder()
                                        .with("setInvisible", boolean.class)
                                        .build());
        NMS_ENTITY_IS_SILENT =
                new MethodResolver(NMS_ENTITY_CLASS.getClazz())
                        .resolveWrapper("isSilent", "R", "ad");
        NMS_ENTITY_SET_SILENT =
                new MethodResolver(NMS_ENTITY_CLASS.getClazz())
                        .resolveWrapper(
                                ResolverQuery.builder()
                                        .with("b", boolean.class)
                                        .with("setSilent", boolean.class)
                                        .build());
        NMS_ENTITY_IS_INVULNERABLE =
                new MethodResolver(NMS_ENTITY_CLASS.getClazz())
                        .resolveWrapper(
                                ResolverQuery.builder()
                                        .with("isInvulnerable", DAMAGE_SOURCE.getClazz())
                                        .build());
        ENTITY_LIVING_C =
                new MethodResolver(ENTITY_LIVING.getClazz())
                        .resolveWrapper(
                                ResolverQuery.builder()
                                        .with("c", NBT_TAG_COMPOUND.getClazz())
                                        .build());
        ENTITY_LIVING_F =
                new MethodResolver(ENTITY_LIVING.getClazz())
                        .resolveWrapper(
                                ResolverQuery.builder()
                                        .with("f", NBT_TAG_COMPOUND.getClazz())
                                        .build());
        NBT_TAG_COMPOUND_SET_INT =
                new MethodResolver(NBT_TAG_COMPOUND.getClazz())
                        .resolveWrapper(
                                ResolverQuery.builder()
                                        .with("setInt", String.class, int.class)
                                        .build());
        NMS_ENTITY_YAW =
                new FieldResolver(NMS_ENTITY_CLASS.getClazz())
                        .resolveAccessor(
                                ResolverQuery.builder()
                                        .with("yaw", float.class)
                                        .with("ay", float.class)
                                        .build());
        NMS_ENTITY_PITCH =
                new FieldResolver(NMS_ENTITY_CLASS.getClazz())
                        .resolveAccessor(
                                ResolverQuery.builder()
                                        .with("pitch", float.class)
                                        .with("az", float.class)
                                        .build());
        NMS_ENTITY_INVULNERABLE =
                new FieldResolver(NMS_ENTITY_CLASS.getClazz())
                        .resolveAccessor(
                                ResolverQuery.builder()
                                        .with("invulnerable", boolean.class)
                                        .build());
        ENTITY_ARMOR_STAND_INVULNERABLE =
                new FieldResolver(ENTITY_ARMOR_STAND.getClazz())
                        .resolveAccessor(
                                ResolverQuery.builder()
                                        .with("by", boolean.class)
                                        .with("bz", boolean.class)
                                        .with("bA", boolean.class)
                                        .with("bG", boolean.class)
                                        .with("bD", boolean.class)
                                        .with("armorStandInvisible", boolean.class)
                                        .with("ce", boolean.class)
                                        .with("h", boolean.class)
                                        .build());
        DAMAGE_SOURCE_GENERIC =
                new FieldResolver(DAMAGE_SOURCE.getClazz()).resolveAccessor("GENERIC", "n");
        ENTITY_COUNTER_FIELD =
                new FieldResolver(EntityReflection.NMS_ENTITY_CLASS.getClazz())
                        .resolveAccessor("entityCount");
        if (ENTITY_COUNTER_FIELD.getField() == null) {
            ENTITY_COUNTER_FIELD = new FieldResolver(EntityReflection.NMS_ENTITY_CLASS.getClazz()).resolveByFirstExtendingTypeAccessor(AtomicInteger.class);
        }
    }

    public static int getFreeEntityId() {
        Object entityCount = ENTITY_COUNTER_FIELD.get(null);
        if (entityCount instanceof AtomicInteger) {
            return ((AtomicInteger) entityCount).incrementAndGet();
        } else {
            ENTITY_COUNTER_FIELD.set(null, (int) entityCount + 1);
        }
        return (int) entityCount;
    }

    /**
     * Gets the {@link BoundingBox} for the provided {@link Entity}.
     *
     * <p>
     *
     * @param entity Entity to get its BoundingBox
     * @param height Entity's height
     * @return BoundingBox for the entity, or null if couldn't get
     */
    public static BoundingBox getBoundingBox(Entity entity, float height) {
        final Object handle = BukkitReflection.getHandle(entity);
        final Object nms_bb = NMS_ENTITY_GET_BOUNDING_BOX.invoke(handle);

        int i = 0;

        if (Version.SERVER_VERSION.isNewerEquals(Version.v1_17_R1)) i = 1;

        FieldResolver resolver = new FieldResolver(nms_bb.getClass());
        final double min_x = resolver.resolveIndexAccessor(i++).get(nms_bb);
        final double min_y = (double) resolver.resolveIndexAccessor(i++).get(nms_bb) - height;
        final double min_z = resolver.resolveIndexAccessor(i++).get(nms_bb);

        final double max_x = resolver.resolveIndexAccessor(i++).get(nms_bb);
        final double max_y = (double) resolver.resolveIndexAccessor(i++).get(nms_bb) - height;
        final double max_z = resolver.resolveIndexAccessor(i++).get(nms_bb);

        return new BoundingBox(new Vector(min_x, min_y, min_z), new Vector(max_x, max_y, max_z));
    }

    /**
     * Gets the {@link BoundingBox} for the provided {@link Entity}. The accuracy is not guaranteed
     * when calculating the entity's height.
     *
     * <p>
     *
     * @param entity Entity to get its BoundingBox
     * @return BoundingBox for the entity, or null if couldn't get
     */
    public static BoundingBox getBoundingBox(Entity entity) {
        final Object handle = BukkitReflection.getHandle(entity);
        final float head_height = NMS_ENTITY_GET_HEAD_HEIGHT.invoke(handle);

        return EntityReflection.getBoundingBox(entity, head_height);
    }

    /**
     * This method makes the provided {@code entity} invisible to the desired target players.
     *
     * <p>Note that after calling this, the entity cannot be made visible. Instead the entity will
     * be invisible if the player left the server and joins it again.
     *
     * <p>
     *
     * @param entity Entity to make invisible to the {@code targets} players
     * @param targets Players that will not can see the entity
     */
    public static void setInvisibleTo(Entity entity, Player... targets) {
        Object packet =
                PACKET_PLAY_OUT_ENTITY_DESTROY_CONSTRUCTOR.newInstance(entity.getEntityId());
        for (Player target : targets) {
            if (target.isOnline()) {
                BukkitReflection.sendPacket(target, packet);
            }
        }
    }

    /**
     * Sets whether the provided {@code entity} will have AI.
     *
     * <p>
     *
     * @param entity Target entity
     * @param ai Whether the entity will have AI or not
     */
    public static void setAI(LivingEntity entity, boolean ai) {
        if (Version.SERVER_VERSION.isOlder(Version.v1_9_R2)) {

            Object handle = BukkitReflection.getHandle(entity);
            Object nbt = NBT_TAG_COMPOUND_CONSTRUCTOR.newInstance();

            ENTITY_LIVING_C.invoke(handle, nbt);
            NBT_TAG_COMPOUND_SET_INT.invoke(nbt, "NoAI", (ai ? 0 : 1));
            ENTITY_LIVING_F.invoke(handle, nbt);
        } else {
            entity.setAI(ai);
        }
    }

    /**
     * Sets whether the provided {@code entity} is will have collisions.
     *
     * <p>
     *
     * @param entity Target entity
     * @param collidable Whether to enable collisions for the entity
     */
    public static void setCollidable(LivingEntity entity, boolean collidable) {
        if (Version.SERVER_VERSION.isNewerEquals(Version.v1_9_R2)) {
            entity.setCollidable(collidable);
        }
    }

    /**
     * Gets the entity's current position.
     *
     * <p>
     *
     * @param entity Entity to get
     * @return Copy of Location containing the position of the desired entity, or null if couldn't
     *     get
     */
    public static Location getLocation(Entity entity) {
        Object handle = BukkitReflection.getHandle(entity);

        final double x = NMS_ENTITY_LOC_X.invoke(handle);
        final double y = NMS_ENTITY_LOC_Y.invoke(handle);
        final double z = NMS_ENTITY_LOC_Z.invoke(handle);

        final float yaw = NMS_ENTITY_YAW.get(handle);
        final float pitch = NMS_ENTITY_PITCH.get(handle);

        return new Location(entity.getWorld(), x, y, z, yaw, pitch);
    }

    /**
     * Sets the location of the provided nms entity. Note that this method doesn't teleport the
     * entity.
     *
     * <p>
     *
     * @param entity Nms entity to set
     * @param x Location's x
     * @param y Location's y
     * @param z Location's z
     * @param yaw Rotation around axis y
     * @param pitch Rotation around axis x
     */
    public static void setLocation(
            Object entity, double x, double y, double z, float yaw, float pitch) {
        try {
            MethodReflection.invoke(
                    MethodReflection.get(
                            entity.getClass(),
                            "setLocation",
                            double.class,
                            double.class,
                            double.class,
                            float.class,
                            float.class),
                    entity,
                    x,
                    y,
                    z,
                    yaw,
                    pitch);
        } catch (IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException
                | NoSuchMethodException
                | SecurityException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the location of the provided entity. Note that this method doesn't teleport the entity.
     *
     * <p>
     *
     * @param entity Entity to set
     * @param x Location's x
     * @param y Location's y
     * @param z Location's z
     * @param yaw Rotation around axis y
     * @param pitch Rotation around axis x
     */
    public static void setLocation(
            Entity entity, double x, double y, double z, float yaw, float pitch) {
        Object handle = BukkitReflection.getHandle(entity);
        NMS_ENTITY_SET_LOCATION.invoke(handle, x, y, z, yaw, pitch);
    }

    /**
     * Sets the location of the provided entity. Note that this method doesn't teleport the entity.
     *
     * <p>
     *
     * @param entity Entity to set
     * @param location Location for the entity
     */
    public static void setLocation(Entity entity, Location location) {
        EntityReflection.setLocation(
                entity,
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getYaw(),
                location.getPitch());
    }

    /**
     * Sets the location of the provided nms entity. Note that this method doesn't teleport the
     * entity.
     *
     * <p>
     *
     * @param entity Nms entity to set
     * @param location Location for the entity
     */
    public static void setLocation(Object entity, Location location) {
        EntityReflection.setLocation(
                entity,
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getYaw(),
                location.getPitch());
    }

    /**
     * Sets the yaw and pitch of the provided entity. Note that this method doesn't teleport the
     * entity.
     *
     * <p>Also {@link #setLocation(Entity, double, double, double, float, float)} is recommended to
     * be used instead.
     *
     * <p>
     *
     * @param entity Entity the entity to set
     * @param yaw New yaw
     * @param pitch New pitch
     */
    public static void setYawPitch(Entity entity, float yaw, float pitch) {
        Object handle = BukkitReflection.getHandle(entity);
        NMS_ENTITY_SET_YAW_PITCH.invoke(handle, yaw, pitch);
    }

    /**
     * Gets whether the provided entity is visible or not.
     *
     * <p>
     *
     * @param entity Entity to check
     * @return true if visible
     */
    public static boolean isVisible(Entity entity) {
        Object handle = BukkitReflection.getHandle(entity);
        return !(boolean) NMS_ENTITY_IS_INVISIBLE.invoke(handle);
    }

    /**
     * Sets whether the provided entity is visible or not.
     *
     * <p>
     *
     * @param entity Entity to set
     * @param visible {@code true} = visible, {@code false} = invisible
     */
    public static void setVisible(Entity entity, boolean visible) {
        Object handle = BukkitReflection.getHandle(entity);
        NMS_ENTITY_SET_INVISIBLE.invoke(handle, !visible);
    }

    /**
     * Gets whether the provided entity is silent or not.
     *
     * <p>
     *
     * @param entity Entity to check
     * @return true if silent
     */
    public static boolean isSilent(Entity entity) {
        Object handle = BukkitReflection.getHandle(entity);
        return NMS_ENTITY_IS_SILENT.invoke(handle);
    }

    /**
     * Sets whether the provided entity is silent or not.
     *
     * <p>
     *
     * @param entity Entity to set
     * @param silent true = silent, false = not silent
     */
    public static void setSilent(Entity entity, boolean silent) {
        Object handle = BukkitReflection.getHandle(entity);
        NMS_ENTITY_SET_SILENT.invoke(handle, silent);
    }

    /**
     * Gets whether or not the provided entity is invulnerable.
     *
     * <p>
     *
     * @param entity Entity to check
     * @return true if invulnerable
     */
    public static boolean isInvulnerable(Entity entity) {
        if (Version.SERVER_VERSION.isOlderEquals(Version.v1_9_R2)) {
            Object handle = BukkitReflection.getHandle(entity);
            Object generic_damage = DAMAGE_SOURCE_GENERIC.get(null);

            return NMS_ENTITY_IS_INVULNERABLE.invoke(handle, generic_damage);
        } else {
            return entity.isInvulnerable();
        }
    }

    /**
     * Sets whether the provided entity is invulnerable or not.
     *
     * <p>When an entity is invulnerable it can only be damaged by players in creative mode.
     *
     * <p>
     *
     * @param entity Entity to set
     * @param invulnerable true = invulnerable, false = vulnerable
     */
    public static void setInvulnerable(Entity entity, boolean invulnerable) {
        if (Version.SERVER_VERSION.isOlderEquals(Version.v1_9_R2)) {
            Object handle = BukkitReflection.getHandle(entity);
            NMS_ENTITY_INVULNERABLE.set(handle, invulnerable);
        } else {
            entity.setInvulnerable(invulnerable);
        }
    }

    /**
     * Sets the provided {@link ArmorStand} as invulnerable. This method is to be used instead of
     * {@link #setInvulnerable(Entity, boolean)}.
     *
     * <p>
     *
     * @param stand Armor stand to set
     */
    public static void setInvulnerable(ArmorStand stand, boolean invulnerable) {
        ENTITY_ARMOR_STAND_INVULNERABLE.set(BukkitReflection.getHandle(stand), !invulnerable);
    }
}
