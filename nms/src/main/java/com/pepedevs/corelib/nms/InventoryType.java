package com.pepedevs.corelib.nms;

public enum InventoryType {

    BEACON(org.bukkit.event.inventory.InventoryType.BEACON, 1),
    BREWING_STAND(org.bukkit.event.inventory.InventoryType.BREWING, 4),
    CHEST(org.bukkit.event.inventory.InventoryType.CHEST, 27),
    ANVIL(org.bukkit.event.inventory.InventoryType.ANVIL, 2),
    CRAFTING_TABLE(org.bukkit.event.inventory.InventoryType.CRAFTING, 9),
    ENCHANTING_TABLE(org.bukkit.event.inventory.InventoryType.ENCHANTING, 2),
    DISPENSER(org.bukkit.event.inventory.InventoryType.DISPENSER, 9),
    FURNACE(org.bukkit.event.inventory.InventoryType.FURNACE, 3),
    HOPPER(org.bukkit.event.inventory.InventoryType.HOPPER, 5);

    InventoryType(org.bukkit.event.inventory.InventoryType spigotType, int size) {
        this.SPIGOT_TYPE = spigotType;
        this.SIZE = (byte) size;
    }

    public final org.bukkit.event.inventory.InventoryType SPIGOT_TYPE;
    public final byte SIZE;

    public static InventoryType fromSpigot(org.bukkit.event.inventory.InventoryType type) {
        switch (type) {
            case BEACON: {
                return BEACON;
            }
            case CRAFTING: {
                return CRAFTING_TABLE;
            }
            case BREWING: {
                return BREWING_STAND;
            }
            case HOPPER: {
                return HOPPER;
            }
            case ANVIL: {
                return ANVIL;
            }
            case ENCHANTING: {
                return ENCHANTING_TABLE;
            }
            case DISPENSER: {
                return DISPENSER;
            }
            case FURNACE: {
                return FURNACE;
            }
            case CHEST:
            case ENDER_CHEST:
            case BARREL:
            case SHULKER_BOX:
            default: {
                return CHEST;
            }
        }
    }

}
