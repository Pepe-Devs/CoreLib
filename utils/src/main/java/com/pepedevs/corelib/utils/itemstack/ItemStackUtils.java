package com.pepedevs.corelib.utils.itemstack;

import com.cryptomorin.xseries.XMaterial;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.pepedevs.corelib.adventure.AdventureUtils;
import com.pepedevs.corelib.utils.StringUtils;
import com.pepedevs.corelib.utils.io.GameProfileBuilder;
import com.pepedevs.corelib.utils.io.UUIDFetcher;
import com.pepedevs.corelib.utils.reflection.bukkit.PlayerReflection;
import com.pepedevs.corelib.utils.reflection.general.FieldReflection;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/** Represents a useful class for handling ItemStacks. */
public class ItemStackUtils {

    /**
     * Whether the field 'durability' is present in the {@link ItemStack} class of this server
     * version.
     */
    public static final boolean AVAILABLE_DURABILITY_FIELD;

    static {
        /* durability field */
        boolean durability_field = false;
        try {
            durability_field =
                    ItemStack.class.getDeclaredField("durability") != null;
        } catch (NoSuchFieldException | SecurityException e) {
            durability_field = false;
        }
        AVAILABLE_DURABILITY_FIELD = durability_field;
    }

    /**
     * Gets the {@link ItemMeta} of the given {@link ItemStack}.
     *
     * <p>A new {@link ItemMeta} will created if necessary.
     *
     * <p>
     *
     * @param stack {@link ItemStack} to get
     * @return {@link ItemMeta} of the given {@link ItemMeta}
     */
    public static ItemMeta getItemMeta(ItemStack stack) {
        ItemMeta meta = stack.getItemMeta();
        if (meta == null) {
            meta = Bukkit.getItemFactory().getItemMeta(stack.getType());
        }
        return meta;
    }

    /**
     * Extracts the name from the {@link ItemMeta} of an {@link ItemStack}.
     *
     * <p>
     *
     * @param stack {@link ItemStack} to extract
     * @param strip_colors Strip colors?
     * @return Display name of the given {@link ItemStack} or an empty string if it doesn't have a name
     */
    public static String extractName(ItemStack stack, boolean strip_colors) {
        if (stack == null || stack.getItemMeta() == null) {
            return "";
        }

        String displayName = stack.getItemMeta().getDisplayName();
        return displayName == null
                ? ""
                : (strip_colors ? StringUtils.stripColors(displayName) : displayName);
    }

    /**
     * Extracts the name from the {@link ItemMeta} of an {@link ItemStack}.
     *
     * <p>
     *
     * @param stack {@link ItemStack} to extract
     * @return Display name of the given {@link ItemStack} or an empty string if it doesn't have a name
     */
    public static Component extractName(ItemStack stack) {
        if (stack == null || stack.getItemMeta() == null) {
            return Component.empty();
        }

        String displayName = stack.getItemMeta().getDisplayName();
        return displayName == null
                ? Component.empty()
                : AdventureUtils.fromVanillaString(displayName);
    }

    /**
     * Extracts the lore from the {@link ItemMeta} of an {@link ItemStack}.
     *
     * <p>
     *
     * @param stack {@link ItemStack} to extract
     * @param strip_colors Strip colors of the lore?
     * @return Lore of the given {@link ItemStack} or an empty list if it doesn't have lore
     */
    public static List<String> extractLore(ItemStack stack, boolean strip_colors) {
        List<String> lore = new ArrayList<>();
        if (stack != null && stack.getItemMeta() != null && stack.getItemMeta().getLore() != null) {
            lore = new ArrayList<>(stack.getItemMeta().getLore());
            if (strip_colors) {
                for (int i = 0; i < lore.size(); i++) {
                    lore.set(i, StringUtils.stripColors(lore.get(i)));
                }
            }
        }
        return lore;
    }

    /**
     * Extracts the lore from the {@link ItemMeta} of an {@link ItemStack}.
     *
     * <p>
     *
     * @param stack {@link ItemStack} to extract
     * @return Lore of the given {@link ItemStack} or an empty list if it doesn't have lore
     */
    public static List<Component> extractLore(ItemStack stack) {
        List<Component> lore = new ArrayList<>();
        if (stack != null && stack.getItemMeta() != null && stack.getItemMeta().getLore() != null) {
            for (String str : stack.getItemMeta().getLore()) {
                lore.add(AdventureUtils.fromVanillaString(str));
            }
        }
        return lore;
    }

    /**
     * Set {@link ItemStack} name and lore.
     *
     * <p>
     *
     * @param itemStack ItemStack to modify
     * @param name New name
     * @param lore New lore
     * @return Modified ItemStack
     */
    public static ItemStack setNameLore(ItemStack itemStack, String name, List<String> lore) {
        ItemStack ot = itemStack;
        if (name != null) {
            ot = setName(itemStack, name);
        }
        return setLore(ot, lore);
    }

    /**
     * Set {@link ItemStack} name and lore.
     *
     * <p>
     *
     * @param itemStack ItemStack to modify
     * @param name New name
     * @param lore New lore
     * @return Modified ItemStack
     */
    public static ItemStack setNameLore(ItemStack itemStack, Component name, List<Component> lore) {
        ItemStack ot = itemStack;
        if (name != null) {
            ot = setName(itemStack, name);
        }
        return setLoreComponent(ot, lore);
    }

    /**
     * Set {@link ItemStack} set ItemStack name.
     *
     * <p>
     *
     * @param itemStack ItemStack to modify
     * @param name New name
     * @return Modified ItemStack
     */
    public static ItemStack setName(ItemStack itemStack, String name) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) {
            meta = Bukkit.getItemFactory().getItemMeta(itemStack.getType());
        }

        if (meta == null) {
            return itemStack;
        }

        meta.setDisplayName(StringUtils.translateAlternateColorCodes(name));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    /**
     * Set {@link ItemStack} set ItemStack name.
     *
     * <p>
     *
     * @param itemStack ItemStack to modify
     * @param name New name
     * @return Modified ItemStack
     */
    public static ItemStack setName(ItemStack itemStack, Component name) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) {
            meta = Bukkit.getItemFactory().getItemMeta(itemStack.getType());
        }

        if (meta == null) {
            return itemStack;
        }

        meta.setDisplayName(AdventureUtils.toVanillaString(name));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    /**
     * Set {@link ItemStack} set ItemStack lore.
     *
     * <p>
     *
     * @param itemStack ItemStack to modify
     * @param lore New lore
     * @return Modified ItemStack
     */
    public static ItemStack setLore(ItemStack itemStack, List<String> lore) {
        if (lore == null || lore.isEmpty()) {
            return itemStack;
        }

        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) {
            meta = Bukkit.getItemFactory().getItemMeta(itemStack.getType());
        }

        if (meta == null) {
            return itemStack;
        }

        for (int x = 0; x < lore.size(); x++) {
            lore.set(x, StringUtils.translateAlternateColorCodes(lore.get(x)));
        }

        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    /**
     * Set {@link ItemStack} set ItemStack lore.
     *
     * <p>
     *
     * @param itemStack ItemStack to modify
     * @param lore New lore
     * @return Modified ItemStack
     */
    public static ItemStack setLoreComponent(ItemStack itemStack, List<Component> lore) {
        if (lore == null || lore.isEmpty()) {
            return itemStack;
        }

        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) {
            meta = Bukkit.getItemFactory().getItemMeta(itemStack.getType());
        }

        if (meta == null) {
            return itemStack;
        }

        List<String> loreString = new ArrayList<>();
        for (Component component : lore) {
            loreString.add(AdventureUtils.toVanillaString(component));
        }

        meta.setLore(loreString);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    /**
     * Add enchant to {@link ItemStack}.
     *
     * <p>
     *
     * @param stack ItemStack
     * @param enchant Enchantment
     * @param level Enchant level
     * @return Enchanted ItemStack
     */
    public static ItemStack addEnchantment(final ItemStack stack, final Enchantment enchant, int level) {
        // get item meta.
        ItemMeta meta = stack.getItemMeta();
        if (meta == null) {
            meta = Bukkit.getItemFactory().getItemMeta(stack.getType());
        }

        // add enchant.
        meta.addEnchant(enchant, level, true);

        // update meta.
        stack.setItemMeta(meta);
        return stack;
    }

    /**
     * Check two items have the same lore.
     *
     * <p>
     *
     * @param i1 First ItemStack
     * @param i2 Second ItemStack
     * @return true if it has the same lore
     */
    public static boolean equalsLore(final ItemStack i1, final ItemStack i2) {
        // check not null.
        if ((i1 != null) == (i2 != null)) {
            // check meta.
            if ((i1.getItemMeta() != null) == (i2.getItemMeta() != null)) {
                // check has item meta.
                if (i1.getItemMeta() == null) {
                    return true;
                }

                // check meta lore.
                if (i1.getItemMeta().hasLore() == i2.getItemMeta().hasLore()) {
                    // check has lore.
                    if (!i1.getItemMeta().hasLore()) {
                        return true;
                    }

                    // get lores.
                    final List<String> lore1 = i1.getItemMeta().getLore();
                    final List<String> lore2 = i2.getItemMeta().getLore();

                    // compare lores.
                    for (String line : lore1) {
                        // check not null
                        if (line == null) {
                            continue;
                        }

                        // check if the other contains.
                        if (!lore2.contains(line)) {
                            return false;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Get the player textured skull {@link ItemStack}.<br>
     * <b>Should run asynchronous</b>
     *
     * <p>
     *
     * @param owner Player texture owner
     * @return Skull ItemStack textured with the player skin
     */
    public static ItemStack getSkull(Player owner) {
        return owner != null
                ? ItemStackUtils.createSkull(ItemStackUtils.getTexture(owner), owner.getName())
                : ItemStackUtils.getSkullMaterial(1);
    }

    /**
     * Get the player textured skull {@link ItemStack}.<br>
     * <b>Should run asynchronous</b>
     *
     * <p>
     *
     * @param owner UUID of texture owner
     * @return Skull ItemStack textured with the player skin
     */
    public static ItemStack getSkull(UUID owner) {
        return owner != null
                ? ItemStackUtils.createSkull(ItemStackUtils.getTexture(owner), "Head")
                : ItemStackUtils.getSkullMaterial(1);
    }

    /**
     * Get the player textured skull {@link ItemStack}.<br>
     * <b>Should run asynchronous</b>
     *
     * <p>
     *
     * @param texture Texture of the Skull item
     * @return Skull ItemStack textured with the player skin
     */
    public static ItemStack getSkull(final String texture) {
        return texture != null
                ? ItemStackUtils.createSkull(texture, "Head")
                : ItemStackUtils.getSkullMaterial(1);
    }

    /**
     * Create a Skull ItemStack.
     *
     * <p>
     *
     * @param texture Texture
     * @param displayname Item display name
     * @return Textured skull item stack
     */
    private static ItemStack createSkull(String texture, String displayname) {
        // get item and get meta.
        final ItemStack stack = ItemStackUtils.getSkullMaterial(1);
        final SkullMeta meta =
                ItemStackUtils.setSkullMeta(((SkullMeta) stack.getItemMeta()), texture);

        // set display name.
        meta.setDisplayName(StringUtils.translateAlternateColorCodes(displayname));

        // update meta.
        stack.setItemMeta(meta);
        return stack;
    }

    /**
     * Get texture from owner name.
     *
     * <p>
     *
     * @param owner Skull owner
     * @return Texture
     */
    private static String getTexture(String owner) {
        UUID uuid = UUIDFetcher.getInstance().getUUID(owner);
        return ItemStackUtils.getTexture(uuid);
    }

    private static String getTexture(UUID owner) {
        GameProfile profile = GameProfileBuilder.getInstance().fetch(owner);
        return profile.getProperties().get("textures").iterator().next().getValue();
    }

    private static String getTexture(Player owner) {
        // get Game Profile and return property.
        GameProfile profile = PlayerReflection.getGameProfile(owner);
        return profile.getProperties().get("textures").iterator().next().getValue();
    }

    /**
     * Sets the skull texture property.
     *
     * <p>
     *
     * @param skullMeta {@link SkullMeta}
     * @param texture Texture
     * @return Textured SkullMeta
     */
    private static SkullMeta setSkullMeta(final SkullMeta skullMeta, final String texture) {
        // get profile.
        GameProfile profile = new GameProfile(UUID.randomUUID(), "CoreHead");

        // put textures property.
        profile.getProperties().put("textures", new Property("texture", texture));

        // set field.
        try {
            FieldReflection.setValue(skullMeta, "profile", profile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return skullMeta;
    }

    /**
     * Get a Skull Item stack.
     *
     * <p>
     *
     * @param amount Stack amount
     * @return Skull Item Stack
     */
    private static ItemStack getSkullMaterial(int amount) {
        ItemStack item = XMaterial.PLAYER_HEAD.parseItem();
        item.setAmount(amount);
        return item;
    }

    /**
     * Get {@link PlayerInventory} contents.
     *
     * <p>
     *
     * @param inventory Inventory
     * @return PlayerInventory list of contents
     */
    public static List<ItemStack> getAllContents(
            final PlayerInventory inventory, boolean addArmorContents) {
        final List<ItemStack> contents = new ArrayList<ItemStack>();
        for (int x = 0; x < 2; x++) {
            if (x > 0 && !addArmorContents) {
                break;
            }

            contents.addAll(
                    Arrays.asList(
                            (x == 0 ? inventory.getContents() : inventory.getArmorContents())));
        }
        return contents;
    }

    /**
     * Returns an empty {@link ItemStack}.
     *
     * <p>
     *
     * @return Empty {@link ItemStack}
     */
    public static ItemStack getEmptyStack() {
        return new ItemStack(Material.AIR);
    }
}
