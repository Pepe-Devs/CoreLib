package com.pepedevs.corelib.packets;

import com.pepedevs.corelib.utils.PluginHandler;
import com.pepedevs.corelib.utils.reflection.bukkit.PlayerReflection;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.util.*;

/** Simple player's channels handler, for handling the receiving-sending packets. */
public final class PacketChannelHandler extends PluginHandler {

    /** All the packet listeners that will be run when a packet is being receive-send. */
    private static final Map<PacketListener.Priority, Set<RegisteredPacketListener>> PACKET_LISTENERS =
            new HashMap<>();

    public PacketChannelHandler(Plugin plugin) {
        super(plugin);
        Bukkit.getPluginManager()
                .registerEvents(new PlayerInjector(), this.getPlugin());
    }

    /**
     * Registers the provided {@link PacketListener} that will be run when a packet is being sending
     * or receiving ( packets which its name is the same as the provided ).
     *
     * <p>
     *
     * @param packet_name Name of the packets this listener will manage.
     * @param priority Priority of the listener.
     * @param listener Listener to run.
     */
    public void addPacketListener(
            final String packet_name, final PacketListener.Priority priority, final PacketListener listener) {
        Set<RegisteredPacketListener> by_priority = PACKET_LISTENERS.get(priority);
        if (by_priority == null) {
            PACKET_LISTENERS.put(priority, (by_priority = new HashSet<>()));
        }

        by_priority.add(new RegisteredPacketListener(packet_name, listener));
    }

    /**
     * Unregisters the provided {@link PacketListener}.
     *
     * <p>
     *
     * @param listener Listener to unregister.
     */
    public void removePacketListener(final PacketListener listener) {
        Set<RegisteredPacketListener> containing = null;
        RegisteredPacketListener removing = null;
        for (Set<RegisteredPacketListener> by_priority : PACKET_LISTENERS.values()) {
            for (RegisteredPacketListener registered : by_priority) {
                if (registered.listener.equals(listener)) {
                    containing = by_priority;
                    removing = registered;
                    break;
                }
            }

            if (removing != null) {
                break;
            }
        }

        containing.remove(removing);
    }

    @Override
    protected boolean isAllowMultipleInstances() {
        return false;
    }

    @Override
    protected boolean isSingleInstanceForAllPlugin() {
        return true;
    }

    /** Class representing the registered version of a {@link PacketListener}. */
    private static class RegisteredPacketListener {

        private final String packet_name;
        private final PacketListener listener;

        private RegisteredPacketListener(final String packet_name, final PacketListener listener) {
            this.packet_name = packet_name;
            this.listener = listener;
        }

    }

    /**
     * Listener that injects any player that joins the server.
     *
     * <p>Also this class injects the players that are already connected to the server when is
     * constructed.
     */
    private static class PlayerInjector implements Listener {

        private PlayerInjector() {
            Bukkit.getOnlinePlayers().forEach(PlayerInjector::inject);
        }

        private static void inject(final Player player) {
            /* unject before injecting */
            PlayerInjector.unject(player);

            /* injecting */
            final Channel channel = PlayerReflection.getChannel(player);
            final PlayerChannelHandler handler = new PlayerChannelHandler(player);

            channel.pipeline().addBefore("packet_handler", "PacketInjector", handler);
        }

        private static void unject(final Player player) {
            final Channel channel = PlayerReflection.getChannel(player);
            if (channel.pipeline().get("PacketInjector") != null) {
                channel.pipeline().remove("PacketInjector");
            }
        }

        @EventHandler(priority = EventPriority.LOWEST)
        public void handleJoin(final PlayerJoinEvent event) {
            PlayerInjector.inject(event.getPlayer());
        }

        @EventHandler(priority = EventPriority.LOWEST)
        public void handleQuit(final PlayerQuitEvent event) {
            PlayerInjector.unject(event.getPlayer());
        }

    }

    /** The duplex channel handler for handling the packets when receiving or sending. */
    private static class PlayerChannelHandler extends ChannelDuplexHandler {

        private final Player player;

        public PlayerChannelHandler(final Player player) {
            this.player = player;
        }

        @Override
        public void channelRead(final ChannelHandlerContext context, final Object packet)
                throws Exception {
            final PacketEvent event = new PacketEvent(player, packet);

            synchronized (PACKET_LISTENERS) {
                final List<PacketListener> listeners = getListenersFor(packet);
                for (PacketListener listener : listeners) {
                    // we're taking care of exceptions that can cause a kick.
                    try {
                        listener.onReceiving(event);
                    } catch (Throwable ex) {
                        ex.printStackTrace();
                    }
                }
            }

            if (!event.isCancelled()) {
                super.channelRead(context, packet);
            }
        }

        @Override
        public void write(
                final ChannelHandlerContext context,
                final Object packet,
                final ChannelPromise promise)
                throws Exception {
            final PacketEvent event = new PacketEvent(player, packet);

            synchronized (PACKET_LISTENERS) {
                final List<PacketListener> listeners = getListenersFor(packet);
                for (PacketListener listener : listeners) {
                    // we're taking care of exceptions that can cause a kick.
                    try {
                        listener.onSending(event);
                    } catch (Throwable ex) {
                        ex.printStackTrace();
                    }
                }
            }

            if (!event.isCancelled()) {
                super.write(context, packet, promise);
            }
        }

        private List<PacketListener> getListenersFor(final Object packet) {
            final List<PacketListener> listeners = new ArrayList<>();
            for (PacketListener.Priority priority : PacketListener.Priority.values()) {
                final Set<RegisteredPacketListener> by_priority = PACKET_LISTENERS.get(priority);
                if (by_priority == null) {
                    continue;
                }

                for (RegisteredPacketListener listener : by_priority) {
                    if (listener.packet_name.equals(packet.getClass().getSimpleName())) {
                        listeners.add(listener.listener);
                    }
                }
            }
            return listeners;
        }
    }
}
