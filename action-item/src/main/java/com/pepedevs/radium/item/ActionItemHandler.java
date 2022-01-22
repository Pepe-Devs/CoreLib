package com.pepedevs.radium.item;

import com.pepedevs.radium.events.EventHandler;
import com.pepedevs.radium.events.EventUtils;
import com.pepedevs.radium.utils.PluginHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/** Handler Class for Action Items. */
public class ActionItemHandler extends PluginHandler {

    /** Stores the active {@link ActionItem}s. */
    protected static final Set<ActionItem> ACTION_ITEMS = new HashSet<>();

    public ActionItemHandler(Plugin plugin) {
        super(plugin);
        register();

        // registering executors
        for (EventPriority priority : EventPriority.values()) {
            EventHandler.listen(
                    this.getPlugin(),
                    PlayerInteractEvent.class,
                    priority,
                    new Consumer<PlayerInteractEvent>() {
                        @Override
                        public void accept(PlayerInteractEvent event) {
                            Player player = event.getPlayer();
                            ItemStack item = event.getItem();

                            if (item != null && event.getAction() != Action.PHYSICAL) {
                                ActionItem action_item =
                                        ACTION_ITEMS.stream()
                                                .filter(value -> value.getPriority() == priority)
                                                .filter(value -> value.isThis(item))
                                                .findAny()
                                                .orElse(null);

                                if (action_item != null) {
                                    ActionItem.EnumAction action_type;
                                    boolean sneaking = player.isSneaking();
                                    boolean sprinting = player.isSprinting();
                                    boolean left_click = EventUtils.isLeftClick(event.getAction());
                                    boolean right_click =
                                            EventUtils.isRightClick(event.getAction());

                                    if (sneaking) {
                                        action_type =
                                                left_click
                                                        ? ActionItem.EnumAction.LEFT_CLICK_SNEAKING
                                                        : (right_click
                                                                ? ActionItem.EnumAction
                                                                        .RIGHT_CLICK_SNEAKING
                                                                : null);
                                    } else if (sprinting) {
                                        action_type =
                                                left_click
                                                        ? ActionItem.EnumAction.LEFT_CLICK_SPRINTING
                                                        : (right_click
                                                                ? ActionItem.EnumAction
                                                                        .RIGHT_CLICK_SPRINTING
                                                                : null);
                                    } else {
                                        action_type =
                                                left_click
                                                        ? ActionItem.EnumAction.LEFT_CLICK
                                                        : (right_click
                                                                ? ActionItem.EnumAction.RIGHT_CLICK
                                                                : null);
                                    }

                                    if (action_type != null) {
                                        action_item.onActionPerform(
                                                event.getPlayer(), action_type, event);
                                    } else {
                                        throw new IllegalStateException(
                                                "couldn't determine performed action");
                                    }
                                }
                            }
                        }
                    });
        }
    }

    /**
     * Registers the specified {@link ActionItem}.
     *
     * <p>
     *
     * @param item Action item to register.
     * @return true if the provided item is not already registered.
     */
    public static boolean register(ActionItem item) {
        return ACTION_ITEMS.add(item);
    }

    /**
     * Unregisters the specified {@link ActionItem}.
     *
     * <p>
     *
     * @param item Action item to unregister.
     * @return true if the provided item was unregistered successfully.
     */
    public static boolean unregister(ActionItem item) {
        return ACTION_ITEMS.remove(item);
    }

    @Override
    protected boolean isAllowMultipleInstances() {
        return false;
    }

    @Override
    protected boolean isSingleInstanceForAllPlugin() {
        return false;
    }
}
